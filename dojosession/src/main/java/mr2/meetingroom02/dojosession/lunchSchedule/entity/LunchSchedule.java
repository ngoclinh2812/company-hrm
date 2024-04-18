package mr2.meetingroom02.dojosession.lunchSchedule.entity;

import lombok.*;
import mr2.meetingroom02.dojosession.base.entity.BaseEntity;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class LunchSchedule extends BaseEntity {

    @JsonbDateFormat("yyyy-MM-dd")
    private LocalDate startDate;

    @JsonbDateFormat("yyyy-MM-dd")
    private LocalDate endDate;

    @JsonbDateFormat("yyyy-MM-dd")
    private LocalDate orderDeadline;

}
