package mr2.meetingroom02.dojosession.lunch.dao;

import mr2.meetingroom02.dojosession.base.dao.BaseDAO;
import mr2.meetingroom02.dojosession.lunch.dto.UpcomingWeekMealsDTO;
import mr2.meetingroom02.dojosession.lunch.entity.LunchOrder;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class LunchOrderDAO extends BaseDAO<LunchOrder> {

    @PersistenceContext
    private EntityManager entityManager;

    public LunchOrderDAO() {
        super(LunchOrder.class);
    }

    public List<UpcomingWeekMealsDTO> getNextWeekOrderList() {
        TypedQuery<UpcomingWeekMealsDTO> query = entityManager.createQuery(
                "SELECT NEW mr2.meetingroom02.dojosession.lunch.dto.UpcomingWeekMealsDTO(dept.departmentName, d.name, COUNT(lo.menuDish.id)) " +
                        "FROM LunchOrder lo " +
                        "RIGHT JOIN lo.menuDish md " +
                        "LEFT JOIN md.dish d " +
                        "LEFT JOIN lo.employee e " +
                        "LEFT JOIN e.department dept " +
                        "GROUP BY dept.departmentName, d.name " +
                        "ORDER BY dept.departmentName ASC, d.name ASC", UpcomingWeekMealsDTO.class
        );
        return query.getResultList();
    }
}
