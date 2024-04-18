package mr2.meetingroom02.dojosession.dish.dao;

import mr2.meetingroom02.dojosession.base.dao.BaseDAO;
import mr2.meetingroom02.dojosession.dish.entity.Dish;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Stateless
public class DishDAO extends BaseDAO<Dish> {

    public DishDAO() {
        super(Dish.class);
    }

    public List<Dish> getDishByMenuId(Long menuId) {
        TypedQuery<Dish> query = entityManager.createQuery(
                "SELECT me FROM Meal me WHERE me.menu.id = :menuId",
                Dish.class
        );
        query.setParameter("menuId", menuId);
        return query.getResultList();
    }

    public List<Dish> getDishesByIds(Set<Long> dishIds) {
        TypedQuery<Dish> query = entityManager.createQuery(
                "SELECT d FROM Dish d " +
                        "WHERE d.id IN :dishIds", Dish.class)
                .setParameter("dishIds", dishIds);
        List<Dish> meals = query.getResultList();
        return meals;
    }

    public List<Dish> getAllMealsSelectedWithinThisMonth(LocalDate menuDate) {
        try {
            LocalDateTime startOfMonth = menuDate.atStartOfDay();
            Query query = entityManager.createNamedQuery("mealsWithinTheCurrentMonth", Dish.class)
                    .setParameter("startOfMonth", startOfMonth);
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

}
