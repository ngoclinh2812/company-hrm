package mr2.meetingroom02.dojosession.lunch.dto;

import javax.json.bind.annotation.JsonbDateFormat;
import java.time.LocalDate;
import java.util.List;

public class LunchScheduleResponseDTO {

    private Long id;

    @JsonbDateFormat("yyyy-MM-dd")
    private LocalDate startDate;

    @JsonbDateFormat("yyyy-MM-dd")
    private LocalDate endDate;

    List<MenuDTO> menuList;

}
