package mr2.meetingroom02.dojosession.lunch.service;

import mr2.meetingroom02.dojosession.lunch.dto.CreateLunchScheduleDTO;
import mr2.meetingroom02.dojosession.lunch.dto.LunchScheduleResponseDTO;
import mr2.meetingroom02.dojosession.lunch.dto.MealDTO;
import mr2.meetingroom02.dojosession.lunch.dto.MenuDTO;
import mr2.meetingroom02.dojosession.lunch.entity.LunchSchedule;
import mr2.meetingroom02.dojosession.lunch.entity.Meal;
import mr2.meetingroom02.dojosession.lunch.entity.Menu;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface LunchMapper {

    LunchSchedule toScheduleEntity(CreateLunchScheduleDTO lunchSchedule);

    List<Menu> toMenuEntityList(List<MenuDTO> menuDTOS);

    List<Meal> toMealEntityList(List<MealDTO> mealDTOS);

    LunchScheduleResponseDTO toLunchScheduleDTO(LunchSchedule savedSchedule);
}
