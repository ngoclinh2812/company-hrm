package mr2.meetingroom02.dojosession.lunch.dao;

import mr2.meetingroom02.dojosession.base.dao.BaseDAO;
import mr2.meetingroom02.dojosession.base.exception.NotFoundException;
import mr2.meetingroom02.dojosession.lunch.dto.LunchScheduleResponseDTO;
import mr2.meetingroom02.dojosession.lunch.entity.LunchSchedule;
import mr2.meetingroom02.dojosession.lunch.entity.Meal;
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

    public List<Meal> getAllMealsSelectedWithinThisMonth() {
        try {
            LocalDate currentDate = LocalDate.now();
            int currentYear = currentDate.getYear();
            int currentMonth = currentDate.getMonthValue();

            TypedQuery<Meal> query = entityManager.createQuery(
                    "SELECT meal FROM Meal meal " +
                            "JOIN meal.menu menu " +
                            "WHERE FUNCTION('YEAR', menu.date) = :year " +
                            "AND FUNCTION('MONTH', menu.date) = :month", Meal.class
            );
            query.setParameter("year", currentYear);
            query.setParameter("month", currentMonth);

            List<Meal> meals = query.getResultList();
            return meals;
        } catch (NoResultException e) {
            return null;
        }
    }
}
