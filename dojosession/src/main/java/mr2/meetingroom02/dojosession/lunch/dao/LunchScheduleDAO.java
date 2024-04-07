package mr2.meetingroom02.dojosession.lunch.dao;

import mr2.meetingroom02.dojosession.base.dao.BaseDAO;
import mr2.meetingroom02.dojosession.base.exception.NotFoundException;
import mr2.meetingroom02.dojosession.lunch.dto.LunchScheduleResponseDTO;
import mr2.meetingroom02.dojosession.lunch.entity.Dish;
import mr2.meetingroom02.dojosession.lunch.entity.LunchOrder;
import mr2.meetingroom02.dojosession.lunch.entity.LunchSchedule;
import mr2.meetingroom02.dojosession.lunch.entity.Menu;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

import static mr2.meetingroom02.dojosession.base.exception.message.LunchScheduleExceptionMessage.LUNCH_SCHEDULE_NOT_FOUND;

@Stateless
public class LunchScheduleDAO extends BaseDAO<LunchSchedule> {

    @PersistenceContext
    private EntityManager entityManager;

    public LunchScheduleDAO() {
        super(LunchSchedule.class);
    }

    public LunchSchedule getScheduleLunch(Long scheduleId) throws NotFoundException {
        try {
            TypedQuery<LunchSchedule> query = entityManager.createQuery("SELECT ls FROM LunchSchedule ls WHERE ls.id = :id", LunchSchedule.class)
                    .setParameter("id", scheduleId);
            LunchSchedule lunchSchedule = query.getSingleResult();
            return lunchSchedule;
        } catch (NoResultException e) {
            throw new NotFoundException(LUNCH_SCHEDULE_NOT_FOUND);
        }
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

    public Menu getMenuByDate(LocalDate date) {
        return null;
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

    public List<Dish> getAllMealsSelectedWithinThisMonth() {
        try {
            TypedQuery<Dish> query = entityManager.createNamedQuery("mealsWithinTheCurrentMonth", Dish.class);
            return query.getResultList();
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
}
