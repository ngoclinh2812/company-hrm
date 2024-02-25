package mr2.meetingroom02.dojosession.lunch.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import mr2.meetingroom02.dojosession.lunch.entity.Menu;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class MenuDTO {

    private LocalDate date;

    private List<MealDTO> meals;

}
