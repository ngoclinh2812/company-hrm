package mr2.meetingroom02.dojosession.lunch.entity;

import lombok.*;
import mr2.meetingroom02.dojosession.base.entity.BaseEntity;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

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

}
