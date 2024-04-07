package mr2.meetingroom02.dojosession.lunch.entity;

import lombok.*;
import mr2.meetingroom02.dojosession.base.entity.BaseEntity;
import mr2.meetingroom02.dojosession.employee.entity.Employee;

import javax.persistence.*;

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
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "menu_dish_id")
    private MenuDish menuDish;

}
