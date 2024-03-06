package mr2.meetingroom02.dojosession.lunch.service;

import mr2.meetingroom02.dojosession.lunch.dao.LunchScheduleDAO;
import mr2.meetingroom02.dojosession.lunch.dao.MealDAO;
import mr2.meetingroom02.dojosession.lunch.dao.MenuDAO;
import mr2.meetingroom02.dojosession.lunch.dto.CreateLunchScheduleDTO;
import mr2.meetingroom02.dojosession.lunch.dto.LunchScheduleResponseDTO;
import mr2.meetingroom02.dojosession.lunch.dto.MealDTO;
import mr2.meetingroom02.dojosession.lunch.dto.MenuDTO;
import mr2.meetingroom02.dojosession.lunch.entity.LunchSchedule;
import mr2.meetingroom02.dojosession.lunch.entity.Meal;
import mr2.meetingroom02.dojosession.lunch.entity.Menu;
import org.hibernate.MappingException;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

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
    private LunchMapper lunchMapper;

    @Transactional
    public LunchScheduleResponseDTO createLunchSchedule(CreateLunchScheduleDTO scheduleDTO) throws InternalError, MappingException  {

        LunchSchedule lunchSchedule = lunchMapper.toScheduleEntity(scheduleDTO);

        lunchSchedule.getMenuList().forEach(menu -> {
                menu.setLunchSchedule(lunchSchedule);
                menu.getMeals().forEach(meal -> meal.setMenu(menu));
        });

        LunchSchedule savedLunchSchedule = lunchScheduleDAO.add(lunchSchedule);

        return lunchMapper.toLunchScheduleDTO(savedLunchSchedule);
    }

    public LunchScheduleResponseDTO getLunchScheduleById(Long scheduleId) {
        LunchSchedule lunchSchedule = lunchScheduleDAO.getScheduleLunch(scheduleId);
        if (lunchSchedule != null) {
            List<Menu> menus = menuDAO.getAllByLunchScheduleId(lunchSchedule.getId());

            if (menus != null) {
            for (Menu menu : menus) {
                List<Meal> meals = mealDAO.getMealByMenuId(menu.getId());
                menu.setMeals(meals);
            }
            lunchSchedule.setMenuList(menus);
        }}
        return lunchMapper.toLunchScheduleDTO(lunchSchedule);
    }
}
