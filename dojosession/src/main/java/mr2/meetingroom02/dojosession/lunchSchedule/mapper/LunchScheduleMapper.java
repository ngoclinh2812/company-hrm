package mr2.meetingroom02.dojosession.lunchSchedule.mapper;

import mr2.meetingroom02.dojosession.dish.dto.DishResponseDTO;
import mr2.meetingroom02.dojosession.lunchSchedule.dto.CreateLunchScheduleDTO;
import mr2.meetingroom02.dojosession.lunchSchedule.dto.LunchScheduleResponseDTO;
import mr2.meetingroom02.dojosession.dish.entity.Dish;
import mr2.meetingroom02.dojosession.lunchSchedule.entity.LunchSchedule;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface LunchScheduleMapper {

    LunchSchedule toScheduleEntity(CreateLunchScheduleDTO lunchSchedule);

    LunchScheduleResponseDTO toLunchScheduleDTO(LunchSchedule savedSchedule);

    List<LunchScheduleResponseDTO> toLunchScheduleDTO(List<LunchSchedule> savedSchedule);

    Dish toMealEntity(DishResponseDTO dishDTO);
}
