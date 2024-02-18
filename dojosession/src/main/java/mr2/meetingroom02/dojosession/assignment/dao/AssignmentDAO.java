package mr2.meetingroom02.dojosession.assignment.dao;

import mr2.meetingroom02.dojosession.assignment.entity.Assignment;
import mr2.meetingroom02.dojosession.base.dao.BaseDAO;
import mr2.meetingroom02.dojosession.project.entity.Project;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Dependent
@Stateless
public class AssignmentDAO extends BaseDAO<Assignment> {

    @Default
    @PersistenceContext
    private EntityManager entityManager;

    public AssignmentDAO() {
        super(Assignment.class);
    }

    public List<Project> getProjectsForEmployee(Long employeeId) {
        TypedQuery<Project> query = entityManager.createQuery(
                "SELECT DISTINCT a.project FROM Assignment a WHERE a.employee.id = :employeeId", Project.class);
        query.setParameter("employeeId", employeeId);
        return query.getResultList();
    }

}
