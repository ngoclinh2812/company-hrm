package mr2.meetingroom02.dojosession.lunch.dao;

import mr2.meetingroom02.dojosession.base.dao.BaseDAO;
import mr2.meetingroom02.dojosession.lunch.entity.LunchSchedule;

import javax.ejb.Stateless;

@Stateless
public class LunchScheduleDAO extends BaseDAO<LunchSchedule> {
    public LunchScheduleDAO(Class<LunchSchedule> entityClass) {
        super(entityClass);
    }
}
