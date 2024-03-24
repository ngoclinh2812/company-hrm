package mr2.meetingroom02.dojosession.lunch.dao;

import mr2.meetingroom02.dojosession.base.dao.BaseDAO;
import mr2.meetingroom02.dojosession.department.entity.Department;
import mr2.meetingroom02.dojosession.lunch.entity.Menu;

import javax.ejb.Stateless;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    public Optional<Menu> getMenuInSchedule(LocalDate startDate, LocalDate endDate, Long menuId) {
        try {
            TypedQuery<Menu> query = entityManager.createQuery(
                            "select m from Menu m " +
                                "where (m.date between :startDate and :endDate) and m.id = :menuId", Menu.class)
                    .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate)
                    .setParameter("menuId", menuId);

            return Optional.ofNullable(query.getSingleResult());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
