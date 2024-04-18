package mr2.meetingroom02.dojosession.lunchSchedule.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateLunchScheduleDTO implements Serializable {

    @JsonbDateFormat("yyyy-MM-dd")
    @NotNull
    private LocalDate startDate;

    @JsonbDateFormat("yyyy-MM-dd")
    @NotNull
    private LocalDate endDate;

    @JsonbDateFormat("yyyy-MM-dd")
    @NotNull
    @Column(name = "order_deadline")
    private LocalDate orderDeadline;

}
