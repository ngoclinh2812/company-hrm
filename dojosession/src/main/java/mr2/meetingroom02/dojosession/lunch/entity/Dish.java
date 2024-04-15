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
