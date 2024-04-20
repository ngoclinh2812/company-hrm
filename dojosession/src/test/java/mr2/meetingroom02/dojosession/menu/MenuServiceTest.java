package mr2.meetingroom02.dojosession.menu;

import mr2.meetingroom02.dojosession.base.exception.BadRequestException;
import mr2.meetingroom02.dojosession.base.exception.DuplicateException;
import mr2.meetingroom02.dojosession.base.exception.NotFoundException;
import mr2.meetingroom02.dojosession.base.exception.message.LunchScheduleExceptionMessage;
import mr2.meetingroom02.dojosession.dish.dao.DishDAO;
import mr2.meetingroom02.dojosession.dish.entity.Dish;
import mr2.meetingroom02.dojosession.lunchSchedule.dao.LunchScheduleDAO;
import mr2.meetingroom02.dojosession.lunchSchedule.dto.CreateMenuRequestDTO;
import mr2.meetingroom02.dojosession.lunchSchedule.entity.LunchSchedule;
import mr2.meetingroom02.dojosession.menu.dao.MenuDAO;
import mr2.meetingroom02.dojosession.menu.dto.MenuResponseDTO;
import mr2.meetingroom02.dojosession.menu.entity.Menu;
import mr2.meetingroom02.dojosession.menu.service.MenuService;
import mr2.meetingroom02.dojosession.menuDish.dao.MenuDishDAO;
import mr2.meetingroom02.dojosession.menuDish.entity.MenuDish;
import mr2.meetingroom02.dojosession.protein.Protein;
import mr2.meetingroom02.dojosession.protein.ProteinDAO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static mr2.meetingroom02.dojosession.base.exception.message.DishExceptionMessage.DISH_NOT_FOUND;
import static mr2.meetingroom02.dojosession.base.exception.message.LunchScheduleExceptionMessage.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MenuServiceTest {

    @Mock
    private LunchScheduleDAO lunchScheduleDAO;

    @Mock
    private DishDAO dishDAO;

    @Mock
    private MenuDAO menuDAO;

    @Mock
    private MenuDishDAO menuDishDAO;

    @Mock
    private ProteinDAO proteinDAO;

    @InjectMocks
    private MenuService menuService;

    @Test
    void createMenu_Successfully() throws NotFoundException, DuplicateException, BadRequestException {
        CreateMenuRequestDTO createMenuRequestDTO = CreateMenuRequestDTO.builder()
                .dishIds((Set.of(1L, 2L)))
                .menuDate(LocalDate.of(2024, 12, 3))
                .build();
        Long lunchId = 1L;

        LunchSchedule lunchSchedule = LunchSchedule.builder()
                .startDate(LocalDate.of(2024, 12, 1))
                .endDate(LocalDate.of(2024, 12, 5))
                .build();
        List<Dish> dishes = List.of(
                Dish.builder().name("Dish 1").build(),
                Dish.builder().name("Dish 2").build()
        );

        Menu menu = Menu.builder().build();
        List<MenuDish> menuDishes = List.of(
                MenuDish.builder().dish(dishes.get(0)).build(),
                MenuDish.builder().dish(dishes.get(1)).build()
        );

        when(lunchScheduleDAO.getScheduleLunch(lunchId)).thenReturn(lunchSchedule);
        when(dishDAO.getDishesByIds(anySet())).thenReturn(dishes);
        when(menuDAO.insert(any(Menu.class))).thenReturn(menu);

        MenuResponseDTO actualResponse = menuService.createMenu(createMenuRequestDTO, lunchId);

        assertNotNull(actualResponse);

        verify(lunchScheduleDAO).getScheduleLunch(anyLong());
        verify(dishDAO).getDishesByIds(anySet());
        verify(menuDAO).insert(any(Menu.class));
        verify(menuDishDAO, times(menuDishes.size())).insert(any(MenuDish.class));
    }

    @Test
    void createMenu_LunchScheduleIdNotFound_ThrowBadRequest(){
        LocalDate date = LocalDate.now();
        CreateMenuRequestDTO createMenuRequestDTO = CreateMenuRequestDTO.builder()
                .dishIds((Set.of(1L, 2L)))
                .menuDate(date)
                .build();
        Long lunchId = 100L;

        when(lunchScheduleDAO.getScheduleLunch(lunchId)).thenReturn(null);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            menuService.createMenu(createMenuRequestDTO, lunchId);
        });

        assertEquals(LUNCH_SCHEDULE_NOT_FOUND, exception.getMessage());
    }

    @Test
    void createMenu_EmptyDishIds_Successfully() throws DuplicateException, NotFoundException, BadRequestException {
        CreateMenuRequestDTO createMenuRequestDTO = CreateMenuRequestDTO.builder()
                .dishIds((Set.of()))
                .menuDate(LocalDate.of(2024, 12, 3))
                .build();
        Long lunchId = 1L;

        LunchSchedule lunchSchedule = LunchSchedule.builder()
                .startDate(LocalDate.of(2024, 12, 1))
                .endDate(LocalDate.of(2024, 12, 5))
                .build();

        Menu menu = Menu.builder().build();

        when(lunchScheduleDAO.getScheduleLunch(lunchId)).thenReturn(lunchSchedule);
        when(menuDAO.insert(any(Menu.class))).thenReturn(menu);

        MenuResponseDTO actualResponse = menuService.createMenu(createMenuRequestDTO, lunchId);

        assertNotNull(actualResponse);

        verify(lunchScheduleDAO).getScheduleLunch(anyLong());
        verify(menuDAO).insert(any(Menu.class));
        verify(menuDishDAO, times(0)).insert(any(MenuDish.class));
    }

    @Test
    void createMenu_DuplicatedMealWithinThisMonth_ThrowBadRequest() {
        LocalDate menuDate = LocalDate.of(2024, 12, 3);
        Long lunchId = 1L;

        LunchSchedule lunchSchedule = LunchSchedule.builder()
                .startDate(LocalDate.of(2024, 12, 1))
                .endDate(LocalDate.of(2024, 12, 5))
                .build();

        List<Dish> dishes = List.of(
                Dish.builder().name("Dish 1").build(),
                Dish.builder().name("Dish 2").build()
        );

        when(lunchScheduleDAO.getScheduleLunch(lunchId)).thenReturn(lunchSchedule);
        when(dishDAO.getDishesByIds(anySet())).thenReturn(List.of(dishes.get(0)));
        when(dishDAO.getAllMealsSelectedWithinThisMonth(menuDate)).thenReturn(dishes);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            menuService.createMenu(CreateMenuRequestDTO.builder()
                    .menuDate(menuDate)
                    .dishIds(Set.of(1L))
                    .build(), lunchId);
        });

         assertEquals(LunchScheduleExceptionMessage.mealAlreadySelectedWithinThisMonth(dishes.get(0).getName()), exception.getMessage());
    }

    @Test
    void createMenu_DishIdNotFound_ThrowBadRequest() {
        Set<Long> dishIds = Set.of(1L, 2L);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            menuService.createMenu(CreateMenuRequestDTO.builder()
                    .dishIds(dishIds)
                    .build(), anyLong());
        });
    }

    @Test
    void createMenu_MenuDateOutsideLunchSchedule_ThrowBadRequest() {
        LocalDate menuDate = LocalDate.of(2024, 12, 20);
        LunchSchedule lunchSchedule = LunchSchedule.builder()
                .startDate(LocalDate.of(2024, 12, 1))
                .endDate(LocalDate.of(2024, 12, 15))
                .build();

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            menuService.createMenu(CreateMenuRequestDTO.builder()
                    .menuDate(menuDate)
                    .build(), lunchSchedule.getId());
        });
    }

    @Test
    void createMenu_MenuDateIsInTheWeekend_ThrowBadRequest() {
        LocalDate weekendDate = LocalDate.of(2024, 12, 7);
        Long lunchId = 1L;

        LunchSchedule lunchSchedule = LunchSchedule.builder()
                .startDate(LocalDate.of(2024, 12, 1))
                .endDate(LocalDate.of(2024, 12, 10))
                .build();

        List<Dish> dishes = List.of(
                Dish.builder().name("Dish 1").build(),
                Dish.builder().name("Dish 2").build()
        );

        when(lunchScheduleDAO.getScheduleLunch(lunchId)).thenReturn(lunchSchedule);
        when(dishDAO.getDishesByIds(anySet())).thenReturn(dishes);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            menuService.createMenu(CreateMenuRequestDTO.builder()
                    .menuDate(weekendDate)
                    .dishIds(Set.of(1L, 2L))
                    .build(), lunchId);
        });

         assertEquals(lunchIsNotServedInWeekend(weekendDate), exception.getMessage());
    }

    @Test
    void createMenu_MenuDateAlreadyExists_ThrowBadRequest() {
        LocalDate date = LocalDate.of(2024, 12, 2);
        Long lunchId = 1L;

        LunchSchedule lunchSchedule = LunchSchedule.builder()
                .startDate(LocalDate.of(2024, 12, 1))
                .endDate(LocalDate.of(2024, 12, 5))
                .build();

        List<Dish> dishes = List.of(
                Dish.builder().name("Dish 1").build(),
                Dish.builder().name("Dish 2").build()
        );

        when(lunchScheduleDAO.getScheduleLunch(lunchId)).thenReturn(lunchSchedule);
        when(menuDAO.getMenuByDate(date)).thenReturn(Menu.builder().menuDate(date).build());
        when(dishDAO.getDishesByIds(anySet())).thenReturn(dishes);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            menuService.createMenu(CreateMenuRequestDTO.builder()
                    .menuDate(date)
                    .dishIds(Set.of(1L, 2L))
                    .build(), lunchId);
        });

         assertEquals(LunchScheduleExceptionMessage.menuAlreadyExisted(date), exception.getMessage());
    }

}
