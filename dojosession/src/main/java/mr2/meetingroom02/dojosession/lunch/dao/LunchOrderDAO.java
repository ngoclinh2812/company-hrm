package mr2.meetingroom02.dojosession.lunch.dao;

import mr2.meetingroom02.dojosession.base.dao.BaseDAO;
import mr2.meetingroom02.dojosession.lunch.entity.LunchOrder;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class LunchOrderDAO extends BaseDAO<LunchOrder> {

    @PersistenceContext
    private EntityManager entityManager;

    public LunchOrderDAO() {
        super(LunchOrder.class);
    }

}
