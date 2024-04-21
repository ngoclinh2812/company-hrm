package mr2.meetingroom02.dojosession.lunchOrder;

import mr2.meetingroom02.dojosession.base.exception.BadRequestException;
import mr2.meetingroom02.dojosession.base.exception.NotFoundException;
import mr2.meetingroom02.dojosession.dish.entity.Dish;
import mr2.meetingroom02.dojosession.employee.dao.EmployeeDAO;
import mr2.meetingroom02.dojosession.employee.entity.Employee;
import mr2.meetingroom02.dojosession.lunchOrder.dao.LunchOrderDAO;
import mr2.meetingroom02.dojosession.lunchOrder.dto.LunchOrderResponseDTO;
import mr2.meetingroom02.dojosession.lunchOrder.entity.LunchOrder;
import mr2.meetingroom02.dojosession.lunchOrder.service.LunchOrderService;
import mr2.meetingroom02.dojosession.lunchSchedule.dao.LunchScheduleDAO;
import mr2.meetingroom02.dojosession.lunchSchedule.dto.CreateLunchOrderRequestDTO;
import mr2.meetingroom02.dojosession.lunchSchedule.entity.LunchSchedule;
import mr2.meetingroom02.dojosession.menu.entity.Menu;
import mr2.meetingroom02.dojosession.menuDish.dao.MenuDishDAO;
import mr2.meetingroom02.dojosession.menuDish.entity.MenuDish;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LunchOrderServiceTest {

    @Mock
    private CreateLunchOrderRequestDTO createLunchOrderRequestDTO;

    @Mock
    private EmployeeDAO employeeDAO;

    @Mock
    private MenuDishDAO menuDishDAO;

    @Mock
    private LunchOrderDAO lunchOrderDAO;

    @Mock
    private LunchScheduleDAO lunchScheduleDAO;

    @InjectMocks
    private LunchOrderService lunchOrderService;

    @Test
    void createLunchOrder_Successfully() throws NotFoundException, BadRequestException {

        String employeeEmail = "test@example.com";
        Employee employee = Employee.builder().email(employeeEmail).build();
        when(employeeDAO.findEmployeeByEmail(employeeEmail)).thenReturn(employee);

        Long menuDishId = 1L;
        Menu menu = Menu.builder().menuDate(LocalDate.of(2024, 12, 1)).build();
        MenuDish menuDish = MenuDish.builder()
                .dish(new Dish())
                .menu(menu)
                .build();
        when(menuDishDAO.findById(menuDishId)).thenReturn(Optional.of(menuDish));

        Long lunchId = 1L;
        LunchSchedule lunchSchedule = LunchSchedule.builder()
                .startDate(LocalDate.of(2024, 12, 1))
                .endDate(LocalDate.of(2024, 12, 5))
                .orderDeadline(LocalDate.of(2024, 12, 3))
                .build();
        when(lunchScheduleDAO.findById(createLunchOrderRequestDTO.getLunchScheduleId())).thenReturn(Optional.ofNullable(lunchSchedule));

        when(createLunchOrderRequestDTO.getMenuDishId()).thenReturn(Collections.singleton(menuDishId));
        when(lunchOrderDAO.insert(any(LunchOrder.class))).thenReturn(new LunchOrder());

        LunchOrderResponseDTO result = lunchOrderService.createLunchOrder(createLunchOrderRequestDTO, employeeEmail);

        assertNotNull(result);
        assertEquals(1, result.getMenuDishes().size());
        assertEquals(menu.getMenuDate(), result.getMenuDishes().get(0).getMenuDate());
    }

    @Test
    void createLunchOrder_OrderDateIsAfterOrderDeadline_ThrowBadRequest() {
        String employeeEmail = "test@example.com";
        Employee employee = Employee.builder().email(employeeEmail).build();

        Long menuDishId = 1L;
        Menu menu = Menu.builder().menuDate(LocalDate.of(2024, 12, 1)).build();
        MenuDish menuDish = MenuDish.builder()
                .dish(new Dish())
                .menu(menu)
                .build();

        Long lunchScheduleId = 1L;
        LunchSchedule lunchSchedule = LunchSchedule.builder()
                .startDate(LocalDate.of(2024, 4, 1))
                .endDate(LocalDate.of(2024, 4, 5))
                .orderDeadline(LocalDate.of(2024, 4, 3))
                .build();
        when(lunchScheduleDAO.findById(lunchScheduleId)).thenReturn(Optional.ofNullable(lunchSchedule));

        CreateLunchOrderRequestDTO createLunchOrderRequestDTO = CreateLunchOrderRequestDTO.builder()
                .menuDishId(Collections.singleton(menuDishId))
                .lunchScheduleId(lunchScheduleId)
                .build();

        BadRequestException exception = assertThrows(BadRequestException.class, () ->
                lunchOrderService.createLunchOrder(createLunchOrderRequestDTO, employeeEmail));

        assertEquals("This lunch schedule has been closed.", exception.getMessage());

    }

    @Test
    void createLunchOrder_menuDishIdNotFound_ThrowBadRequest() {
        String employeeEmail = "test@example.com";
        CreateLunchOrderRequestDTO createLunchOrderRequestDTO = CreateLunchOrderRequestDTO.builder()
                .menuDishId(Collections.singleton(1L))
                .lunchScheduleId(1L)
                .build();
        when(employeeDAO.findEmployeeByEmail(employeeEmail)).thenReturn(Employee.builder().email(employeeEmail).build());

        Long menuDishId = 1L;
        Menu menu = Menu.builder().menuDate(LocalDate.of(2024, 5, 1)).build();
        MenuDish menuDish = MenuDish.builder()
                .dish(new Dish())
                .menu(menu)
                .build();
        when(menuDishDAO.findById(menuDishId)).thenReturn(Optional.empty());

        Long lunchScheduleId = 1L;
        LunchSchedule lunchSchedule = LunchSchedule.builder()
                .startDate(LocalDate.of(2024, 5, 1))
                .endDate(LocalDate.of(2024, 5, 5))
                .orderDeadline(LocalDate.of(2024, 5, 3))
                .build();
        when(lunchScheduleDAO.findById(lunchScheduleId)).thenReturn(Optional.ofNullable(lunchSchedule));

        BadRequestException exception = assertThrows(BadRequestException.class, () ->
                lunchOrderService.createLunchOrder(createLunchOrderRequestDTO, employeeEmail));

        assertEquals("Invalid Menu Dish id", exception.getMessage());
    }

    @Test
    void createLunchOrder_MoreThanOneDishForAMenu() {
        MenuDish menuDish1 = createMenuDish(LocalDate.of(2024, 4, 1));
        MenuDish menuDish2 = createMenuDish(LocalDate.of(2024, 4, 1));
        Set<LocalDate> selectedDates = new HashSet<>();

        selectedDates.add(menuDish1.getMenu().getMenuDate());

        BadRequestException exception = assertThrows(BadRequestException.class, () ->
                validateSelectedDate(menuDish2, selectedDates));

        String expectedErrorMessage = "You can only select one menu dish per day.";
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    private MenuDish createMenuDish(LocalDate menuDate) {
        Menu menu = Menu.builder().menuDate(menuDate).build();
        return MenuDish.builder().menu(menu).build();
    }

    private void validateSelectedDate(MenuDish menuDish, Set<LocalDate> selectedDates) throws BadRequestException {
        if (!selectedDates.add(menuDish.getMenu().getMenuDate())) {
            throw new BadRequestException("You can only select one menu dish per day.");
        }
    }



}
