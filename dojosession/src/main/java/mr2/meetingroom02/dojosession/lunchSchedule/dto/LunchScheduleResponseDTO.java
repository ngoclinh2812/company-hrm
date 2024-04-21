package mr2.meetingroom02.dojosession.lunchSchedule.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import mr2.meetingroom02.dojosession.menu.dto.CreateMenuRequestDTO;

import javax.json.bind.annotation.JsonbDateFormat;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LunchScheduleResponseDTO implements Serializable {

    private Long id;

    @JsonbDateFormat("yyyy-MM-dd")
    private LocalDate startDate;

    @JsonbDateFormat("yyyy-MM-dd")
    private LocalDate endDate;

    List<CreateMenuRequestDTO> menuList;

    @JsonbDateFormat("yyyy-MM-dd")
    private LocalDate orderDeadline;
}
