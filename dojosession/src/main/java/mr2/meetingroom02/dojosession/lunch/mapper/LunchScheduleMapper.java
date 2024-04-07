package mr2.meetingroom02.dojosession.lunch.mapper;

import mr2.meetingroom02.dojosession.lunch.dto.CreateLunchScheduleDTO;
import mr2.meetingroom02.dojosession.lunch.dto.LunchScheduleResponseDTO;
import mr2.meetingroom02.dojosession.lunch.dto.MealDTO;
import mr2.meetingroom02.dojosession.lunch.dto.MenuDTO;
import mr2.meetingroom02.dojosession.lunch.entity.Dish;
import mr2.meetingroom02.dojosession.lunch.entity.LunchSchedule;
import mr2.meetingroom02.dojosession.lunch.entity.Menu;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface LunchScheduleMapper {

    LunchSchedule toScheduleEntity(CreateLunchScheduleDTO lunchSchedule);

    List<Dish> toMealEntityList(List<MealDTO> mealDTOS);

    LunchScheduleResponseDTO toLunchScheduleDTO(LunchSchedule savedSchedule);

    Dish toMealEntity(MealDTO mealDTO);
}
