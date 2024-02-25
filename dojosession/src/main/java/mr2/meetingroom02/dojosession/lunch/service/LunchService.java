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

    public LunchScheduleResponseDTO createLunchSchedule(CreateLunchScheduleDTO scheduleDTO) throws InternalError, MappingException  {
        LunchSchedule lunchSchedule = LunchSchedule.builder()
                .startDate(scheduleDTO.getStartDate())
                .endDate(scheduleDTO.getEndDate())
                .build();

        LunchSchedule savedSchedule = lunchScheduleDAO.add(lunchSchedule);

        List<MenuDTO> menuDTOS = scheduleDTO.getMenuList();

        List<Menu> menuList = lunchMapper.toMenuEntityList(menuDTOS);

        for (int i = 0; i < menuList.size(); i++) {
            menuDAO.add(menuList.get(i));
        }

        List<MealDTO> mealDTOS = menuDTOS.stream()
                .flatMap(menuDTO -> menuDTO.getMeals().stream())
                .collect(Collectors.toList());

        List<Meal> mealList = lunchMapper.toMealEntityList(mealDTOS);

        for (int i = 0; i < mealList.size(); i++) {
            mealDAO.add(mealList.get(i));
        }

        LunchScheduleResponseDTO lunchScheduleResponseDTO = lunchMapper.toLunchScheduleDTO(savedSchedule);

        return lunchScheduleResponseDTO;
    }
}
