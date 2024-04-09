package mr2.meetingroom02.dojosession.lunch.entity;

import lombok.*;
import mr2.meetingroom02.dojosession.base.entity.BaseEntity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@NamedNativeQuery(
        name = "mealsWithinTheCurrentMonth",
        query = "SELECT meal.* " +
                "FROM Dish dish " +
                "JOIN Menu menu ON meal.menu_id = menu.id " +
                "WHERE date_trunc('month', menu.date) = date_trunc('month', current_date)",
        resultClass = Dish.class
)
public class Dish extends BaseEntity {

    @Column(name = "dish_name")
    private String name;

    @Column(name = "calories")
    private Integer calories;

    @ManyToOne
    @JoinColumn(name = "protein_id")
    private Protein protein;

    @OneToMany(mappedBy = "dish")
    private Set<MenuDish> menuDishes;
}
