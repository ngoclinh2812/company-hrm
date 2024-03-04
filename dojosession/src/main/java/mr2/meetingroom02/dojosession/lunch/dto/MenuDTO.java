package mr2.meetingroom02.dojosession.lunch.dto;

import lombok.*;
import mr2.meetingroom02.dojosession.lunch.entity.Menu;

import javax.json.bind.annotation.JsonbDateFormat;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuDTO implements Serializable {

    @JsonbDateFormat("yyyy-MM-dd")
    private LocalDate date;

    private List<MealDTO> meals;

}
