package mr2.meetingroom02.dojosession.menuDish.entity;

import lombok.*;
import mr2.meetingroom02.dojosession.base.entity.BaseEntity;
import mr2.meetingroom02.dojosession.dish.entity.Dish;
import mr2.meetingroom02.dojosession.menu.entity.Menu;

import javax.persistence.*;

@Entity
@Table
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuDish extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne
    @JoinColumn(name = "dish_id")
    private Dish dish;


}
