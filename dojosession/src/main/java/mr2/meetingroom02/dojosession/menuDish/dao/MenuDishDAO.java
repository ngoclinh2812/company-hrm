package mr2.meetingroom02.dojosession.menuDish.dao;

import mr2.meetingroom02.dojosession.base.dao.BaseDAO;
import mr2.meetingroom02.dojosession.menuDish.entity.MenuDish;

import javax.ejb.Stateless;

@Stateless
public class MenuDishDAO extends BaseDAO<MenuDish> {
    public MenuDishDAO() {
        super(MenuDish.class);
    }
}
