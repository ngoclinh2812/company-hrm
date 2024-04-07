package mr2.meetingroom02.dojosession.lunch.service;

import mr2.meetingroom02.dojosession.base.exception.NotFoundException;
import mr2.meetingroom02.dojosession.lunch.dao.DishDAO;
import mr2.meetingroom02.dojosession.lunch.dao.LunchScheduleDAO;
import mr2.meetingroom02.dojosession.lunch.dao.MenuDAO;
import mr2.meetingroom02.dojosession.lunch.dao.MenuDishDao;
import mr2.meetingroom02.dojosession.lunch.dto.CreateMenuRequestDTO;
import mr2.meetingroom02.dojosession.lunch.dto.response.DishResponseDto;
import mr2.meetingroom02.dojosession.lunch.dto.response.MenuResponseDTO;
import mr2.meetingroom02.dojosession.lunch.entity.Dish;
import mr2.meetingroom02.dojosession.lunch.entity.LunchSchedule;
import mr2.meetingroom02.dojosession.lunch.entity.Menu;
import mr2.meetingroom02.dojosession.lunch.entity.MenuDish;
import mr2.meetingroom02.dojosession.lunch.mapper.MenuMapper;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Set;

@Stateless
public class MenuService {

    @Inject
    private DishDAO dishDao;

    @Inject
    private MenuDAO menuDAO;

    @Inject
    private MenuDishDao menuDishDao;

    @Inject
    private MenuMapper menuMapper;

    @Inject
    private LunchScheduleDAO lunchScheduleDAO;

    public MenuResponseDTO createMenu(CreateMenuRequestDTO createMenuRequestDTO, Long lunchId) throws NotFoundException {

        LunchSchedule lunchSchedule = lunchScheduleDAO.getScheduleLunch(lunchId);
        Set<Long> dishIds = createMenuRequestDTO.getDishIds();
        List<Dish> dishes = dishDao.getDishesByIds(dishIds);
        if (dishes.size() != dishIds.size()) {
            throw new IllegalArgumentException("Invalid one of dish:" + dishIds);
        }
        List<MenuDish> menuDishes = dishes
                .stream()
                .map(ele -> MenuDish.builder().dish(ele).build())
                .toList();

        Menu menu = Menu.builder()
                .lunchSchedule(lunchSchedule)
                .menuDate(createMenuRequestDTO.getMenuDate())
                .menuDish(menuDishes).build();

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
}
