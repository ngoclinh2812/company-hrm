package mr2.meetingroom02.dojosession.lunch.dao;

import mr2.meetingroom02.dojosession.base.dao.BaseDAO;
import mr2.meetingroom02.dojosession.department.entity.Department;
import mr2.meetingroom02.dojosession.lunch.dto.LunchScheduleResponseDTO;
import mr2.meetingroom02.dojosession.lunch.entity.LunchSchedule;

import javax.ejb.Stateless;
import javax.persistence.*;

@Stateless
public class LunchScheduleDAO extends BaseDAO<LunchSchedule> {

    @PersistenceContext
    private EntityManager entityManager;

    public LunchScheduleDAO() {
        super(LunchSchedule.class);
    }

    public LunchSchedule getScheduleLunch(Long scheduleId) throws NoResultException {
        TypedQuery<LunchSchedule> query = entityManager.createQuery("" +
                "SELECT ls FROM LunchSchedule ls WHERE ls.id = :id", LunchSchedule.class)
                .setParameter("id", scheduleId);
        return query.getSingleResult();
    }


        public LunchScheduleResponseDTO getScheduleLunchWithDTO(Long scheduleId) throws NoResultException {
        return entityManager.createQuery(
                "SELECT NEW mr2.meetingroom02.dojosession.lunch.dto.LunchScheduleResponseDTO(ls.id, ls.startDate, ls.endDate, m.date, me.name) " +
                    "FROM LunchSchedule ls " +
                    "LEFT JOIN FETCH ls.menuList m " +
                    "LEFT JOIN FETCH m.mealList me " +
                    "WHERE ls.id = :id",
                LunchScheduleResponseDTO.class
        ).setParameter("id", scheduleId).getSingleResult();
    }
}
