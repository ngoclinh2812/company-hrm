package mr2.meetingroom02.dojosession.lunch.entity;

import lombok.*;
import mr2.meetingroom02.dojosession.base.entity.BaseEntity;
import mr2.meetingroom02.dojosession.employee.entity.Employee;

import javax.persistence.*;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@NamedNativeQuery(
        name = "getLunchOrderByEmployeeAndLunchScheduleId",
        query = "select * from lunch_order lo " +
                "join Employee e on e.id = lo.employee_id " +
                "join lunch_schedule ls on ls.id = lo.schedule_id " +
                "where e.id = :employeeId and ls.id = :lunchScheduleId",
        resultClass = LunchOrder.class
)
@Table(name = "lunch_order")
public class LunchOrder extends BaseEntity {

    @ManyToOne
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private LunchSchedule lunchSchedule;

    @ManyToOne
    @JoinColumn(name = "meal_id")
    private Meal meal;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

}
