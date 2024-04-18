package mr2.meetingroom02.dojosession.lunchOrder.entity;

import lombok.*;
import mr2.meetingroom02.dojosession.base.entity.BaseEntity;
import mr2.meetingroom02.dojosession.employee.entity.Employee;
import mr2.meetingroom02.dojosession.lunchSchedule.dto.UpcomingWeekOrderDishesByDepartmentDTO;
import mr2.meetingroom02.dojosession.menuDish.entity.MenuDish;

import javax.persistence.*;
import java.time.LocalDate;

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
@SqlResultSetMapping(
        name = "UpcomingWeekMealsDTOMapping",
        classes = @ConstructorResult(
                targetClass = UpcomingWeekOrderDishesByDepartmentDTO.class,
                columns = {
                        @ColumnResult(name = "menu_date", type = LocalDate.class),
                        @ColumnResult(name = "department_name"),
                        @ColumnResult(name = "dish_name"),
                        @ColumnResult(name = "count", type = Long.class)
                }
        )
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
