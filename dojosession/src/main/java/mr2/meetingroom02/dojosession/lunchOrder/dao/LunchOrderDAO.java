package mr2.meetingroom02.dojosession.lunchOrder.dao;

import mr2.meetingroom02.dojosession.base.dao.BaseDAO;
import mr2.meetingroom02.dojosession.employee.entity.Employee;
import mr2.meetingroom02.dojosession.lunchOrder.entity.LunchOrder;
import mr2.meetingroom02.dojosession.lunchSchedule.dto.UpcomingWeekOrderDishesByDepartmentDTO;
import mr2.meetingroom02.dojosession.menu.entity.Menu;
import mr2.meetingroom02.dojosession.menuDish.entity.MenuDish;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
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


    public List<LunchOrder> getSelectedMenuDishesForEmployee(MenuDish menuDish, Employee employee) {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<LunchOrder> criteriaQuery = criteriaBuilder.createQuery(LunchOrder.class);
            Root<LunchOrder> root = criteriaQuery.from(LunchOrder.class);

            Join<LunchOrder, Employee> employeeJoin = root.join("employee");
            Join<LunchOrder, MenuDish> menuDishJoin = root.join("menuDish");
            Join<MenuDish, Menu> menuJoin = menuDishJoin.join("menu");

            criteriaQuery.select(root).distinct(true);
            criteriaQuery.where(
                    criteriaBuilder.equal(employeeJoin.get("email"), employee.getEmail()),
                    criteriaBuilder.equal(menuJoin.get("menuDate"), menuDish.getMenu().getMenuDate())
            );

            TypedQuery<LunchOrder> query = entityManager.createQuery(criteriaQuery);
            return query.getResultList();
        } catch (NoResultException e) {
            logger.info("MenuDishDAO - getSelectedMenuDishesForEmployee : noResultFound");
        }
        return List.of();
    }
}
