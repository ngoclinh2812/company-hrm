package mr2.meetingroom02.dojosession.lunch.service;

import mr2.meetingroom02.dojosession.base.exception.BadRequestException;
import mr2.meetingroom02.dojosession.base.exception.DuplicateException;
import mr2.meetingroom02.dojosession.base.exception.NotFoundException;
import mr2.meetingroom02.dojosession.base.exception.message.LunchScheduleExceptionMessage;
import mr2.meetingroom02.dojosession.employee.dao.EmployeeDAO;
import mr2.meetingroom02.dojosession.employee.entity.Employee;
import mr2.meetingroom02.dojosession.lunch.dao.LunchOrderDAO;
import mr2.meetingroom02.dojosession.lunch.dao.LunchScheduleDAO;
import mr2.meetingroom02.dojosession.lunch.dao.MealDAO;
import mr2.meetingroom02.dojosession.lunch.dao.MenuDAO;
import mr2.meetingroom02.dojosession.lunch.dto.CreateLunchOrderDTO;
import mr2.meetingroom02.dojosession.lunch.dto.CreateLunchScheduleDTO;
import mr2.meetingroom02.dojosession.lunch.dto.LunchScheduleResponseDTO;
import mr2.meetingroom02.dojosession.lunch.entity.LunchOrder;
import mr2.meetingroom02.dojosession.lunch.entity.LunchSchedule;
import mr2.meetingroom02.dojosession.lunch.entity.Meal;
import mr2.meetingroom02.dojosession.lunch.entity.Menu;
import mr2.meetingroom02.dojosession.lunch.mapper.LunchMapper;
import org.hibernate.MappingException;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static mr2.meetingroom02.dojosession.base.exception.message.EmployeeExceptionMessage.*;
import static mr2.meetingroom02.dojosession.base.exception.message.LunchScheduleExceptionMessage.*;

public class LunchService {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private LunchScheduleDAO lunchScheduleDAO;

    @Inject
    private MenuDAO menuDAO;

    @Inject
    private MealDAO mealDAO;

    @Inject
    private EmployeeDAO employeeDAO;

    @Inject
    private LunchMapper lunchMapper;

    @Inject
    private LunchOrderDAO lunchOrderDAO;


    @Transactional
    public LunchScheduleResponseDTO createLunchSchedule(CreateLunchScheduleDTO scheduleDTO)
            throws InternalError, MappingException, DuplicateException, BadRequestException {
        checkValidDateSchedule(scheduleDTO.getStartDate(), scheduleDTO.getEndDate());
        LunchSchedule lunchSchedule = lunchMapper.toScheduleEntity(scheduleDTO);
        for (Menu menu : lunchSchedule.getMenuList()) {
            checkValidMenu(menu, lunchSchedule);
            for (Meal meal : menu.getMeals()) {
                checkDuplicatedMealWithinThisMonth(meal);
            }
        }
         LunchSchedule savedLunchSchedule = lunchScheduleDAO.insert(lunchSchedule);
        return lunchMapper.toLunchScheduleDTO(savedLunchSchedule);
    }

    @Transactional
    public LunchOrder createLunchOrder(CreateLunchOrderDTO createLunchOrderDTO) throws BadRequestException {

        LunchOrder lunchOrder = new LunchOrder();

        //validate employee
        Employee employee = employeeDAO.findById(createLunchOrderDTO.getEmployeeId())
                .orElseThrow(() -> new BadRequestException(EMPLOYEE_NOT_FOUND));
        lunchOrder.setEmployee(employee);

        //validate lunch schedule
        LunchSchedule selectedlunchSchedule = lunchScheduleDAO.findById(createLunchOrderDTO.getScheduleId())
                .orElseThrow(() -> new BadRequestException(LUNCH_SCHEDULE_NOT_FOUND));
        if (selectedlunchSchedule.getStartDate().isBefore(LocalDate.now())) {
            throw new BadRequestException(ORDER_FOR_THIS_SCHEDULE_IS_CLOSED);
        }
        LunchOrder lunchByOrderedByUser = lunchScheduleDAO.getLunchByOrderedByUser(employee.getId(), selectedlunchSchedule.getId());
        if (lunchByOrderedByUser != null) {
            throw new BadRequestException(EMPLOYEE_HAS_ALREADY_ORDERED_THIS_LUNCH_SCHEDULE);
        }
        lunchOrder.setLunchSchedule(selectedlunchSchedule);

        Set<Long> mealIdsAdded = new HashSet<>();

        // Validate menus and meals
        for (Map.Entry<Long, Long> entry : createLunchOrderDTO.getMenuMeal().entrySet()) {
            Long menuId = entry.getKey();
            Long mealId = entry.getValue();
            Menu menu = menuDAO.getMenuInSchedule(selectedlunchSchedule.getStartDate(), selectedlunchSchedule.getEndDate(), menuId)
                    .orElseThrow(() -> new BadRequestException(menuNotFoundInLunchSchedule(menuId)));

            boolean mealFoundInMenu = menu.getMeals()
                    .stream()
                    .anyMatch(meal -> meal.getId().equals(mealId));
            if (!mealFoundInMenu) {
                throw new BadRequestException(mealNotFoundInMenu(mealId));
            }
            if (mealIdsAdded.contains(mealId)) {
                throw new BadRequestException(DUPLICATED_MEAL_IN_MENU);
            }

            mealIdsAdded.add(mealId);
            lunchOrder.setMenu(menu);
            Meal meal = mealDAO.findById(mealId).orElseThrow();
            lunchOrder.setMeal(meal);
        }

        LunchOrder savedLunchOrder = lunchOrderDAO.insert(lunchOrder);



        return savedLunchOrder;
    }


    public LunchScheduleResponseDTO getLunchScheduleById(Long scheduleId) throws NotFoundException {
        LunchSchedule lunchSchedule = lunchScheduleDAO.getScheduleLunch(scheduleId);
        if (lunchSchedule == null) {
            throw new NotFoundException(LUNCH_SCHEDULE_NOT_FOUND);
        } else {
            List<Menu> menus = menuDAO.getAllByLunchScheduleId(lunchSchedule.getId());
            if (menus != null) {
                for (Menu menu : menus) {
                    List<Meal> meals = mealDAO.getMealByMenuId(menu.getId());
                    menu.setMeals(meals);
                }
                lunchSchedule.setMenuList(menus);
            }
            return lunchMapper.toLunchScheduleDTO(lunchSchedule);
        }
    }

    private void checkValidDateSchedule(LocalDate startDate, LocalDate endDate) throws BadRequestException {
        if (startDate.isBefore(LocalDate.now())) throw new BadRequestException(START_DATE_IS_BEFORE_CURRENT_DATE);
        if (startDate.isAfter(endDate)) throw new BadRequestException(START_DATE_IS_AFTER_END_DATE);
        checkOverlapSchedule(startDate, endDate);
    }

    private void checkOverlapSchedule(LocalDate startDate, LocalDate endDate) throws BadRequestException {
        List<LunchSchedule> lunchSchedules = lunchScheduleDAO.findOverlapLunchSchedule(startDate, endDate);
        if (!lunchSchedules.isEmpty()) {
            throw new BadRequestException(OVERLAP_LUNCH_SCHEDULE);
        }
    }

    private void checkDuplicatedMealWithinThisMonth(Meal mealInput) throws DuplicateException {
        List<Meal> selectedMealsThisMonth = lunchScheduleDAO.getAllMealsSelectedWithinThisMonth();
        if (selectedMealsThisMonth.contains(mealInput)) {
            throw new DuplicateException(LunchScheduleExceptionMessage.mealAlreadyExistedInTheMenu(mealInput.getName()));
        }
    }

    private void checkValidMenu(Menu menu, LunchSchedule lunchDate) throws DuplicateException, BadRequestException {
        checkMenuOutsideOfSchedule(menu.getDate(), lunchDate);
        checkMenuDateIsInTheWeekend(menu.getDate());
        checkMenuAlreadyExisted(menu.getDate());
    }

    private void checkMenuDateIsInTheWeekend(LocalDate date) throws BadRequestException {
        if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            throw new BadRequestException(lunchIsNotServedInWeekend(date));
        }
    }

    private void checkMenuOutsideOfSchedule(LocalDate menuDate, LunchSchedule lunchDate) throws BadRequestException {
        if (menuDate.isBefore(lunchDate.getStartDate()) || menuDate.isAfter(lunchDate.getEndDate())) {
            throw new BadRequestException(menuDateOutOfLunchSchedulePeriod(lunchDate.getStartDate(), lunchDate.getEndDate()));
        }
    }

    private void checkMenuAlreadyExisted(LocalDate date) throws DuplicateException {
        if (lunchScheduleDAO.getMenuByDate(date) != null)
            throw new DuplicateException(LunchScheduleExceptionMessage.menuAlreadyExisted(date));
    }

//    public LunchScheduleResponseDTO getLunchScheduleByStartDateOrEndDate(LocalDate startDate, LocalDate endDate) {
//        if (startDate != null) {
//
//        }
//        if (endDate != null) {
//
//        }
//        return null;
//    }
}
