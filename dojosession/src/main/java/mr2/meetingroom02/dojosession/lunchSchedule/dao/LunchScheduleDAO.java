package mr2.meetingroom02.dojosession.lunchSchedule.dao;

import mr2.meetingroom02.dojosession.base.dao.BaseDAO;
import mr2.meetingroom02.dojosession.lunchOrder.entity.LunchOrder;
import mr2.meetingroom02.dojosession.lunchSchedule.dto.LunchScheduleResponseDTO;
import mr2.meetingroom02.dojosession.lunchSchedule.entity.*;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Stateless
public class LunchScheduleDAO extends BaseDAO<LunchSchedule> {

    @PersistenceContext
    private EntityManager entityManager;

    public LunchScheduleDAO() {
        super(LunchSchedule.class);
    }

    public LunchSchedule getScheduleLunch(Long scheduleId)  {
            TypedQuery<LunchSchedule> query = entityManager.createQuery("SELECT ls FROM LunchSchedule ls WHERE ls.id = :id", LunchSchedule.class)
                    .setParameter("id", scheduleId);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public LunchScheduleResponseDTO getScheduleLunchWithDTO(Long scheduleId) throws NoResultException {
        return entityManager.createQuery(
                "SELECT NEW mr2.meetingroom02.dojosession.lunchSchedule.dto.LunchScheduleResponseDTO(ls.id, ls.startDate, ls.endDate, m.date, me.name) " +
                        "FROM LunchSchedule ls " +
                        "LEFT JOIN FETCH ls.menuList m " +
                        "LEFT JOIN FETCH m.mealList me " +
                        "WHERE ls.id = :id",
                LunchScheduleResponseDTO.class
        ).setParameter("id", scheduleId).getSingleResult();
    }

    public List<LunchSchedule> findOverlapLunchSchedule(LocalDate startDate, LocalDate endDate) {
        try {
            TypedQuery<LunchSchedule> query = entityManager.createQuery(
                "SELECT ls FROM LunchSchedule ls " +
                        "WHERE :startDate between ls.startDate and ls.endDate " +
                        "OR :endDate between ls.startDate and ls.endDate"
                , LunchSchedule.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate);
            List<LunchSchedule> lunchSchedules = query.getResultList();
            return lunchSchedules;
        } catch (NoResultException e) {
            return null;
        }
    }

    public LunchOrder getLunchByOrderedByUser(Long employeeId, Long lunchScheduleId) {
        try {
            Query query = entityManager.createNamedQuery("getLunchOrderByEmployeeAndLunchScheduleId", LunchOrder.class)
                    .setParameter("employeeId", employeeId)
                    .setParameter("lunchScheduleId", lunchScheduleId);
            return (LunchOrder) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public LunchSchedule getLunchScheduleUpcomingWeek() {
        TypedQuery<LunchSchedule> query = entityManager.createQuery(
                        "SELECT ls FROM LunchSchedule ls " +
                                "WHERE ls.startDate >= :currentDate " +
                                "ORDER BY ls.startDate ASC", LunchSchedule.class)
                .setParameter("currentDate", LocalDate.now())
                .setMaxResults(1);

        return query.getSingleResult();

    }
}
