package mr2.meetingroom02.dojosession.lunchOrder.service;

import mr2.meetingroom02.dojosession.base.exception.BadRequestException;
import mr2.meetingroom02.dojosession.base.exception.NotFoundException;
import mr2.meetingroom02.dojosession.employee.dao.EmployeeDAO;
import mr2.meetingroom02.dojosession.employee.entity.Employee;
import mr2.meetingroom02.dojosession.lunchOrder.dao.LunchOrderDAO;
import mr2.meetingroom02.dojosession.lunchOrder.dto.LunchOrderResponseDTO;
import mr2.meetingroom02.dojosession.lunchOrder.entity.LunchOrder;
import mr2.meetingroom02.dojosession.lunchSchedule.dao.LunchScheduleDAO;
import mr2.meetingroom02.dojosession.menuDish.entity.MenuDish;
import mr2.meetingroom02.dojosession.menuDish.dao.MenuDishDAO;
import mr2.meetingroom02.dojosession.lunchSchedule.dto.CreateLunchOrderRequestDTO;
import mr2.meetingroom02.dojosession.dish.dto.DishResponseDTO;
import mr2.meetingroom02.dojosession.menuDish.dto.MenuDishResponseDTO;
import mr2.meetingroom02.dojosession.lunchSchedule.entity.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public LunchOrderResponseDTO createLunchOrder(CreateLunchOrderRequestDTO createLunchOrderRequestDTO, String employeeEmail) throws NotFoundException, BadRequestException {

        checkLunchScheduleOrderDeadline(createLunchOrderRequestDTO);
        
        Employee employee = employeeDAO.findEmployeeByEmail(employeeEmail);

        List<MenuDishResponseDTO> menuDishResponseDTOS = new ArrayList<>();

        createLunchOrderRequestDTO.getMenuDishId().forEach(menuDishId -> {
            MenuDish menuDish = null;
            try {
                menuDish = menuDishDao.findById(menuDishId).orElseThrow(() -> new NotFoundException("Invalid Menu Dish id"));
            } catch (NotFoundException e) {
                throw new RuntimeException(e);
            }

            LunchOrder lunchOrder = LunchOrder.builder()
                    .employee(employee).
                    menuDish(menuDish).build();

            lunchOrderDAO.insert(lunchOrder);

            DishResponseDTO dishResponseDto = DishResponseDTO.builder()
                    .name(lunchOrder.getMenuDish().getDish().getName())
                    .build();

            menuDishResponseDTOS.add(MenuDishResponseDTO.builder()
                    .menuDate(lunchOrder.getMenuDish().getMenu().getMenuDate())
                    .dishResponseDto(dishResponseDto)
                    .build());

        });

        return LunchOrderResponseDTO.builder()
                .employeeId(employee.getId())
                .menuDishes(menuDishResponseDTOS)
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
