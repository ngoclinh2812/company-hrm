package mr2.meetingroom02.dojosession.lunch.dao;

import mr2.meetingroom02.dojosession.base.dao.BaseDAO;
import mr2.meetingroom02.dojosession.lunch.entity.Meal;

public class MealDAO extends BaseDAO<Meal> {
    public MealDAO(Class<Meal> entityClass) {
        super(entityClass);
    }
}
