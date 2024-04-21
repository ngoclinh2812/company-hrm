package mr2.meetingroom02.dojosession.lunchOrder.service;

import mr2.meetingroom02.dojosession.base.exception.BadRequestException;
import mr2.meetingroom02.dojosession.base.exception.NoResultException;
import mr2.meetingroom02.dojosession.base.exception.NotFoundException;
import mr2.meetingroom02.dojosession.employee.dao.EmployeeDAO;
import mr2.meetingroom02.dojosession.employee.entity.Employee;
import mr2.meetingroom02.dojosession.lunchOrder.dao.LunchOrderDAO;
import mr2.meetingroom02.dojosession.lunchOrder.dto.LunchOrderResponseDTO;
import mr2.meetingroom02.dojosession.lunchOrder.entity.LunchOrder;
import mr2.meetingroom02.dojosession.lunchSchedule.dao.LunchScheduleDAO;
import mr2.meetingroom02.dojosession.lunchSchedule.dto.LunchScheduleResponseDTO;
import mr2.meetingroom02.dojosession.lunchSchedule.dto.UpcomingWeekOrderDishesByDepartmentDTO;
import mr2.meetingroom02.dojosession.lunchSchedule.service.LunchScheduleService;
import mr2.meetingroom02.dojosession.menuDish.entity.MenuDish;
import mr2.meetingroom02.dojosession.menuDish.dao.MenuDishDAO;
import mr2.meetingroom02.dojosession.lunchSchedule.dto.CreateLunchOrderRequestDTO;
import mr2.meetingroom02.dojosession.dish.dto.DishResponseDTO;
import mr2.meetingroom02.dojosession.menuDish.dto.MenuDishResponseDTO;
import mr2.meetingroom02.dojosession.lunchSchedule.entity.*;
import mr2.meetingroom02.dojosession.utils.excel.ExcelExporter;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.NoContentException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Stateless
public class LunchOrderService {

    @Inject
    private LunchOrderDAO lunchOrderDAO;

    @Inject
    private EmployeeDAO employeeDAO;

    @Inject
    private MenuDishDAO menuDishDao;

    @Inject
    private LunchScheduleDAO lunchScheduleDAO;

    @Inject
    private LunchScheduleService lunchScheduleService;

    @Inject
    private ExcelExporter excelExporter;

    public List<UpcomingWeekOrderDishesByDepartmentDTO> getMealsOfEachDepartmentInUpcomingWeek() {
        return lunchOrderDAO.getNextWeekOrderList();
    }

    public byte[] exportExcelMealsInUpcomingWeek() throws IOException, NoResultException {
        LunchScheduleResponseDTO lunchScheduleResponseDTO = lunchScheduleService.getLunchScheduleUpcomingWeek();
        List<UpcomingWeekOrderDishesByDepartmentDTO> upcomingWeekOrderDishesByDepartmentDTOS = lunchOrderDAO.getNextWeekOrderList();
        if (upcomingWeekOrderDishesByDepartmentDTOS.isEmpty()) {
            throw new NoResultException("No order for upcoming week");
        }
        byte[] file = excelExporter.exportToExcel(upcomingWeekOrderDishesByDepartmentDTOS, lunchScheduleResponseDTO);
        return file;
    }


    public LunchOrderResponseDTO createLunchOrder(CreateLunchOrderRequestDTO createLunchOrderRequestDTO, String employeeEmail) throws NotFoundException, BadRequestException {
        checkLunchScheduleOrderDeadline(createLunchOrderRequestDTO);

        Employee employee = employeeDAO.findEmployeeByEmail(employeeEmail);
        List<MenuDishResponseDTO> menuDishResponseDTOS = new ArrayList<>();
        Set<LocalDate> selectedDates = new HashSet<>();

        for (Long menuDishId : createLunchOrderRequestDTO.getMenuDishId()) {
            MenuDish menuDish = null;

            menuDish = findMenuDishById(menuDishId);
            //TODO: validate the user to not create duplicate orders
//                validateMenuDish(menuDish, selectedDates);
            validateSelectedDate(menuDish, selectedDates);


            LunchOrder lunchOrder = createLunchOrderForEmployee(employee, menuDish);
            menuDishResponseDTOS.add(createMenuDishResponseDTO(lunchOrder));
            lunchOrderDAO.insert(lunchOrder);
        }

        return LunchOrderResponseDTO.builder()
                .menuDishes(menuDishResponseDTOS)
                .build();
    }

    private MenuDish findMenuDishById(Long menuDishId) throws NotFoundException, BadRequestException {
        return menuDishDao.findById(menuDishId)
                .orElseThrow(() -> new BadRequestException("Invalid Menu Dish id"));
    }

    private void validateSelectedDate(MenuDish menuDish, Set<LocalDate> selectedDates) throws BadRequestException {
        if (!selectedDates.add(menuDish.getMenu().getMenuDate())) {
            throw new BadRequestException("You can only select one menu dish per day.");
        }
    }

    private LunchOrder createLunchOrderForEmployee(Employee employee, MenuDish menuDish) {
        return LunchOrder.builder()
                .employee(employee)
                .menuDish(menuDish)
                .build();
    }

    private MenuDishResponseDTO createMenuDishResponseDTO(LunchOrder lunchOrder) {
        DishResponseDTO dishResponseDto = DishResponseDTO.builder()
                .name(lunchOrder.getMenuDish().getDish().getName())
                .build();

        return MenuDishResponseDTO.builder()
                .menuDate(lunchOrder.getMenuDish().getMenu().getMenuDate())
                .dishResponseDto(dishResponseDto)
                .build();
    }

    private void checkLunchScheduleOrderDeadline(CreateLunchOrderRequestDTO createLunchOrderRequestDTO) throws NotFoundException, BadRequestException {
        LunchSchedule lunchSchedule = lunchScheduleDAO.findById(createLunchOrderRequestDTO.getLunchScheduleId())
                .orElseThrow(() -> new NotFoundException("Lunch schedule not found"));
        if (lunchSchedule.getOrderDeadline().isBefore(LocalDate.now())) {
            throw new BadRequestException("This lunch schedule has been closed.");
        }
    }
}
