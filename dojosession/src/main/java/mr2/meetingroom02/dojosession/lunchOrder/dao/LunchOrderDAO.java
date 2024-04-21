package mr2.meetingroom02.dojosession.lunchOrder.dao;

import mr2.meetingroom02.dojosession.base.dao.BaseDAO;
import mr2.meetingroom02.dojosession.lunchOrder.entity.LunchOrder;
import mr2.meetingroom02.dojosession.lunchSchedule.dto.UpcomingWeekOrderDishesByDepartmentDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class LunchOrderDAO extends BaseDAO<LunchOrder> {

    @PersistenceContext
    private EntityManager entityManager;

    public LunchOrderDAO() {
        super(LunchOrder.class);
    }

    Logger logger = LogManager.getLogger(LunchOrderDAO.class);

    public List<UpcomingWeekOrderDishesByDepartmentDTO> getNextWeekOrderList() {
        try {
            Query query = entityManager.createNativeQuery(
                    "SELECT m.menu_date AS menu_date, dept.department_name AS department_name, " +
                            "d.dish_name AS dish_name, COUNT(lo.menu_dish_id) AS count " +
                            "FROM lunch_order lo " +
                            "RIGHT JOIN menu_dish md ON lo.menu_dish_id = md.id " +
                            "LEFT JOIN dish d ON md.dish_id = d.id " +
                            "LEFT JOIN employee e ON lo.employee_id = e.id " +
                            "LEFT JOIN department dept ON e.department_id = dept.id " +
                            "LEFT JOIN ( " +
                            "SELECT * FROM menu WHERE schedule_id IN ( " +
                            "SELECT id FROM lunch_schedule " +
                            "WHERE start_date >= date_trunc('week', current_date) + interval '1' day " +
                            "ORDER BY start_date LIMIT 1 " +
                            ") " +
                            ") m ON md.menu_id = m.id " +
                            "JOIN lunch_schedule ls ON m.schedule_id = ls.id " +
                            "WHERE ls.start_date >= date_trunc('week', current_date) + interval '1' day " +
                            "GROUP BY dept.department_name, md.dish_id, d.dish_name, m.menu_date " +
                            "ORDER BY m.menu_date ASC, dept.department_name ASC, d.dish_name ASC",
                    "UpcomingWeekMealsDTOMapping"
            );

            List<UpcomingWeekOrderDishesByDepartmentDTO> weekMealsDTOList = query.getResultList();
            return weekMealsDTOList;
        } catch (NoResultException e) {
            logger.info("LunchOrderDAO - getNextWeekOrderList() : No result return");
        }
        return List.of();
    }


}
