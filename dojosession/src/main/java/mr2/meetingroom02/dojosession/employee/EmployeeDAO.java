package mr2.meetingroom02.dojosession.employee;

import mr2.meetingroom02.dojosession.base.dao.BaseDAO;
import mr2.meetingroom02.dojosession.employee.entity.Employee;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
public class EmployeeDAO extends BaseDAO<Employee> {

    public EmployeeDAO() {
        super(Employee.class);
    }

//    public List<Employee> getAllExceptDeleted() {
//        TypedQuery<Employee> query = entityManager.createQuery(
//                "SELECT e FROM Employee e WHERE e.isDeleted = false", Employee.class);
//
//        List<Employee> nonDeletedEmployees = query.getResultList();
//        return nonDeletedEmployees;
//    }

    public List<Employee> getAllExceptDeleted(int pageSize) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> query = cb.createQuery(Employee.class);
        Root<Employee> from = query.from(Employee.class);
        CriteriaQuery<Employee> select = query.select(from);
        TypedQuery<Employee> typedQuery = entityManager.createQuery(select);
        typedQuery.setFirstResult(0);
        typedQuery.setMaxResults(pageSize);
        List<Employee> employees = typedQuery.getResultList();
        return employees;
    }

    public List<Employee> findAllDeptEmployee(Long departmentId) {
        String jpql = "SELECT e FROM Employee e WHERE e.department.id = :departmentId AND e.isDeleted = false";
        Query query = entityManager.createQuery(jpql, Employee.class);
        query.setParameter("departmentId", departmentId);
        return query.getResultList();
    }
}
