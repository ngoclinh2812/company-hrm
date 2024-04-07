package mr2.meetingroom02.dojosession.lunch.dao;

import mr2.meetingroom02.dojosession.base.dao.BaseDAO;
import mr2.meetingroom02.dojosession.lunch.entity.MenuDish;

import javax.ejb.Stateless;

@Stateless
public class MenuDishDao extends BaseDAO<MenuDish> {
    public MenuDishDao() {
        super(MenuDish.class);
    }
}
