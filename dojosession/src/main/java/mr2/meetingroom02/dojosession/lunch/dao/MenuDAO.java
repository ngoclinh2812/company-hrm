package mr2.meetingroom02.dojosession.lunch.dao;

import mr2.meetingroom02.dojosession.base.dao.BaseDAO;
import mr2.meetingroom02.dojosession.lunch.entity.Menu;

public class MenuDAO extends BaseDAO<Menu> {
    public MenuDAO(Class<Menu> entityClass) {
        super(entityClass);
    }
}
