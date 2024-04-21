package mr2.meetingroom02.dojosession.lunchSchedule.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateLunchOrderRequestDTO {

    @NotNull(message = "Lunch schedule id must not be null")
    private Long lunchScheduleId;

    @NotEmpty(message = "Menu dish id must not be empty")
    private Set<Long> menuDishId;
}
