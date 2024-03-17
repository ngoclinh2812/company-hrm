package mr2.meetingroom02.dojosession.lunch.dao;

import mr2.meetingroom02.dojosession.base.dao.BaseDAO;
import mr2.meetingroom02.dojosession.department.entity.Department;
import mr2.meetingroom02.dojosession.lunch.entity.Menu;

import javax.ejb.Stateless;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class MenuDAO extends BaseDAO<Menu> {
    public MenuDAO() {
        super(Menu.class);
    }

    public List<Menu> getAllByLunchScheduleId(Long scheduleId) {
        TypedQuery<Menu> query = entityManager.createQuery(
                "select m from Menu m where m.lunchSchedule.id = :id", Menu.class
        ).setParameter("id", scheduleId);
        return query.getResultList();
    }
}
