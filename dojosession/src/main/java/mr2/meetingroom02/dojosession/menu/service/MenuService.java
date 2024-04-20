package mr2.meetingroom02.dojosession.menu.service;

import mr2.meetingroom02.dojosession.base.exception.BadRequestException;
import mr2.meetingroom02.dojosession.base.exception.DuplicateException;
import mr2.meetingroom02.dojosession.base.exception.NotFoundException;
import mr2.meetingroom02.dojosession.base.exception.message.LunchScheduleExceptionMessage;
import mr2.meetingroom02.dojosession.dish.dao.DishDAO;
import mr2.meetingroom02.dojosession.dish.entity.Dish;
import mr2.meetingroom02.dojosession.lunchSchedule.dao.*;
import mr2.meetingroom02.dojosession.lunchSchedule.dto.CreateMenuRequestDTO;
import mr2.meetingroom02.dojosession.dish.dto.DishResponseDTO;
import mr2.meetingroom02.dojosession.menu.dto.MenuResponseDTO;
import mr2.meetingroom02.dojosession.lunchSchedule.entity.*;
import mr2.meetingroom02.dojosession.menu.dao.MenuDAO;
import mr2.meetingroom02.dojosession.menu.entity.Menu;
import mr2.meetingroom02.dojosession.menuDish.entity.MenuDish;
import mr2.meetingroom02.dojosession.menuDish.dao.MenuDishDAO;
import mr2.meetingroom02.dojosession.protein.Protein;
import mr2.meetingroom02.dojosession.protein.ProteinDAO;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
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
    private MenuDishDAO menuDishDao;

    @Inject
    private LunchScheduleDAO lunchScheduleDAO;

    @Inject
    private ProteinDAO proteinDAO;

    public MenuResponseDTO createMenu(CreateMenuRequestDTO createMenuRequestDTO, Long lunchId) throws NotFoundException, DuplicateException, BadRequestException {

        LunchSchedule lunchSchedule = lunchScheduleDAO.getScheduleLunch(lunchId);

        checkValidLunchSchedule(lunchSchedule);

        Set<Long> dishIds = createMenuRequestDTO.getDishIds();

        checkValidDishIds(dishIds);

        List<MenuDish> menuDishes = new ArrayList<>();
        List<DishResponseDTO> dishResponseDTOS = new ArrayList<>();

        if (!dishIds.isEmpty()) {
            List<Dish> dishes = dishDao.getDishesByIds(dishIds);
            checkValidDishes(dishes, dishIds);

            for (Dish dish : dishes) {
                checkDuplicatedMealWithinThisMonth(dish, createMenuRequestDTO.getMenuDate());
            }

            menuDishes = dishes.stream()
                    .map(ele ->
                            MenuDish.builder().dish(ele).build()
                    )
                    .toList();

            dishResponseDTOS = dishes
                    .stream()
                    .map(ele -> DishResponseDTO.builder().name(ele.getName()).build())
                    .toList();
        } else {
            menuDishes = List.of();
        }

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

        return MenuResponseDTO.builder()
                .id(menu.getId())
                .menuDate(menu.getMenuDate())
                .dishResponseDTOS(dishResponseDTOS)
                .build();

    }

    private void checkValidDishIds(Set<Long> dishIds) throws BadRequestException {
        if (dishIds.size() >= 10) {
            throw new BadRequestException(ONE_MENU_CAN_NOT_CONTAINS_OVER_20_DISHES);
        }

        if (dishIds.isEmpty()) {
            return;
        }
    }

    private void checkValidDishes(List<Dish> dishes, Set<Long> dishIds) throws NotFoundException, BadRequestException {
        if (dishes.size() != dishIds.size()) {
            throw new NotFoundException(DISH_NOT_FOUND);
        }

        for (Dish ele : dishes) {
            checkDuplicatedProteinTypeWithinNumberOfDays(ele, 3);
        }
    }

    private void checkValidLunchSchedule(LunchSchedule lunchSchedule) throws BadRequestException {
        if (lunchSchedule == null) throw new BadRequestException(LUNCH_SCHEDULE_NOT_FOUND);
    }

    private void checkDuplicatedProteinTypeWithinNumberOfDays(Dish ele, int days) throws BadRequestException {
        List<Protein> proteins = proteinDAO.getProteinsFromPreviousDays(days);
        if (proteins.contains(ele.getProtein())) {
            throw new BadRequestException("Main protein types should not be duplicated within 2 days in the lunch schedules");
        }
    }

    private void checkDuplicatedMealWithinThisMonth(Dish mealInput, LocalDate menuDate) throws DuplicateException, BadRequestException {
        List<Dish> selectedMealsThisMonth = dishDao.getAllMealsSelectedWithinThisMonth(menuDate);
        if (selectedMealsThisMonth.contains(mealInput)) {
            throw new BadRequestException(LunchScheduleExceptionMessage.mealAlreadySelectedWithinThisMonth(mealInput.getName()));
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

    private void checkMenuAlreadyExisted(LocalDate date) throws BadRequestException {
        if (menuDAO.getMenuByDate(date) != null)
            throw new BadRequestException(LunchScheduleExceptionMessage.menuAlreadyExisted(date));
    }
}
