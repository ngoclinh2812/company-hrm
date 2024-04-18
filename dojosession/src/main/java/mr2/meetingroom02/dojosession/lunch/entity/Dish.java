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
        query = "SELECT * FROM dish d " +
                "JOIN menu_dish md ON d.id = md.dish_id " +
                "JOIN menu m ON m.id = md.menu_id " +
                "WHERE date_trunc('month', m.menu_date) = date_trunc('month', CAST(:startOfMonth AS TIMESTAMP)) " +
                "ORDER BY m.menu_date ASC",
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
