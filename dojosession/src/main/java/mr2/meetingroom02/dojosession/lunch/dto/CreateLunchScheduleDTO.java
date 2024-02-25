package mr2.meetingroom02.dojosession.lunch.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.json.bind.annotation.JsonbDateFormat;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class CreateLunchScheduleDTO {

    @JsonbDateFormat("yyyy-MM-dd")
    private LocalDate startDate;

    @JsonbDateFormat("yyyy-MM-dd")
    private LocalDate endDate;

    List<MenuDTO> menuList;

}
