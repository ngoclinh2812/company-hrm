package mr2.meetingroom02.dojosession.lunch.dao;

import mr2.meetingroom02.dojosession.base.dao.BaseDAO;
import mr2.meetingroom02.dojosession.department.entity.Department;
import mr2.meetingroom02.dojosession.lunch.entity.Meal;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class MealDAO extends BaseDAO<Meal> {

    public MealDAO() {
        super(Meal.class);
    }

    public List<Meal> getMealByMenuId(Long menuId) {
        TypedQuery<Meal> query = entityManager.createQuery(
                "SELECT me FROM Meal me WHERE me.menu.id = :menuId",
                Meal.class
        );
        query.setParameter("menuId", menuId);
        return query.getResultList();
    }
}
