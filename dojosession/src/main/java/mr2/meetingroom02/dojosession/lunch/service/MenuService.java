package mr2.meetingroom02.dojosession.lunch.service;

import mr2.meetingroom02.dojosession.base.exception.BadRequestException;
import mr2.meetingroom02.dojosession.base.exception.DuplicateException;
import mr2.meetingroom02.dojosession.base.exception.NotFoundException;
import mr2.meetingroom02.dojosession.base.exception.message.LunchScheduleExceptionMessage;
import mr2.meetingroom02.dojosession.lunch.dao.*;
import mr2.meetingroom02.dojosession.lunch.dto.CreateMenuRequestDTO;
import mr2.meetingroom02.dojosession.lunch.dto.response.DishResponseDto;
import mr2.meetingroom02.dojosession.lunch.dto.response.MenuResponseDTO;
import mr2.meetingroom02.dojosession.lunch.entity.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static mr2.meetingroom02.dojosession.base.exception.message.DishExceptionMessage.DISH_NOT_FOUND;
import static mr2.meetingroom02.dojosession.base.exception.message.LunchScheduleExceptionMessage.*;

@Stateless
public class MenuService {

    @Inject
    private DishDAO dishDao;

    @Inject
    private MenuDAO menuDAO;

    @Inject
    private MenuDishDao menuDishDao;

    @Inject
    private LunchScheduleDAO lunchScheduleDAO;

    @Inject
    private ProteinDAO proteinDAO;

    public MenuResponseDTO createMenu(CreateMenuRequestDTO createMenuRequestDTO, Long lunchId) throws NotFoundException, DuplicateException, BadRequestException {

        LunchSchedule lunchSchedule = lunchScheduleDAO.getScheduleLunch(lunchId);

        Set<Long> dishIds = createMenuRequestDTO.getDishIds();
        List<Dish> dishes = dishDao.getDishesByIds(dishIds);
        if (dishes.size() != dishIds.size()) {
            throw new NotFoundException(DISH_NOT_FOUND);
        }

        dishes.forEach(ele -> {
            try {
                checkDuplicatedMealWithinThisMonth(ele);
            } catch (DuplicateException e) {
                throw new RuntimeException(e);
            }
        });

        List<MenuDish> menuDishes = dishes.stream()
            .map(ele -> {
                try {
                    checkDuplicatedProteinTypeWithinNumberOfDays(ele, 3);
                } catch (BadRequestException e) {
                    throw new RuntimeException(e);
                }
                return MenuDish.builder().dish(ele).build();
            })
            .toList();

        Menu menu = Menu.builder()
                .lunchSchedule(lunchSchedule)
                .menuDate(createMenuRequestDTO.getMenuDate())
                .menuDish(menuDishes).build();

        checkValidMenu(menu, lunchSchedule);

        menuDAO.insert(menu);
        menuDishes.forEach(ele -> {
            ele.setMenu(menu);
            menuDishDao.insert(ele);
        });

        List<DishResponseDto> dishResponseDtos = dishes
                .stream()
                .map(ele -> DishResponseDto.builder().name(ele.getName()).build())
                .toList();

        return MenuResponseDTO.builder()
                .id(menu.getId())
                .menuDate(menu.getMenuDate())
                .dishResponseDtos(dishResponseDtos)
                .build();

    }

    //TODO: validate protein type within
    private void checkDuplicatedProteinTypeWithinNumberOfDays(Dish ele, int days) throws BadRequestException {
        List<Protein> proteins = proteinDAO.getProteinsFromPreviousDays(days);
        if (proteins.contains(ele.getProtein())) {
            throw new BadRequestException("Main protein types should not be duplicated within 2 days");
        }
    }

    private void checkDuplicatedMealWithinThisMonth(Dish mealInput) throws DuplicateException {
        List<Dish> selectedMealsThisMonth = dishDao.getAllMealsSelectedWithinThisMonth();
        if (selectedMealsThisMonth.contains(mealInput)) {
            throw new DuplicateException(LunchScheduleExceptionMessage.mealAlreadySelectedWithinThisMonth(mealInput.getName()));
        }
    }

    private void checkValidMenu(Menu menu, LunchSchedule lunchDate) throws DuplicateException, BadRequestException {
        checkMenuOutsideOfSchedule(menu.getMenuDate(), lunchDate);
        checkMenuDateIsInTheWeekend(menu.getMenuDate());
        checkMenuAlreadyExisted(menu.getMenuDate());
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
        if (menuDAO.getMenuByDate(date) != null)
            throw new DuplicateException(LunchScheduleExceptionMessage.menuAlreadyExisted(date));
    }
}
