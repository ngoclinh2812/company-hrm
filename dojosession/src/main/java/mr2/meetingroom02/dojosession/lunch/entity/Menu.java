package mr2.meetingroom02.dojosession.lunch.entity;

import lombok.*;
import mr2.meetingroom02.dojosession.base.entity.BaseEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@NamedNativeQuery(
        name = "mealsWithinTheCurrentMonth",
        query = "select * from menu m " +
                "join menu_dish md on m.id = md.menu_id " +
                "join dish d on d.id = md.dish_id " +
                "WHERE date_trunc('month', m.menu_date) = date_trunc('month', current_date) " +
                "order by m.menu_date asc;",
        resultClass = Menu.class
)
public class Menu extends BaseEntity {

    @Column(name = "menu_date", unique = true)
    private LocalDate menuDate;

    @OneToMany(mappedBy = "menu")
    private List<MenuDish> menuDish;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private LunchSchedule lunchSchedule;

}
