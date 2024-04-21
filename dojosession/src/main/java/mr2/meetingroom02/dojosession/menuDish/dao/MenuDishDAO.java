package mr2.meetingroom02.dojosession.menuDish.dao;

import mr2.meetingroom02.dojosession.base.dao.BaseDAO;
import mr2.meetingroom02.dojosession.employee.entity.Employee;
import mr2.meetingroom02.dojosession.lunchOrder.entity.LunchOrder;
import mr2.meetingroom02.dojosession.menuDish.entity.MenuDish;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Stateless
public class MenuDishDAO extends BaseDAO<MenuDish> {

    public MenuDishDAO() {
        super(MenuDish.class);
    }

    Logger logger = LogManager.getLogger(MenuDishDAO.class);


}
