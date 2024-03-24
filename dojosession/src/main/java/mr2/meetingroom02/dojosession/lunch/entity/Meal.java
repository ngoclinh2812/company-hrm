package mr2.meetingroom02.dojosession.lunch.entity;

import lombok.*;
import mr2.meetingroom02.dojosession.base.entity.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@NamedNativeQuery(
        name = "mealsWithinTheCurrentMonth",
        query = "SELECT meal.* " +
                "FROM Meal meal " +
                "JOIN Menu menu ON meal.menu_id = menu.id " +
                "WHERE date_trunc('month', menu.date) = date_trunc('month', current_date)",
        resultClass = Meal.class
)
public class Meal extends BaseEntity {

    private String name;

}
