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

//    public List<Employee> sortAllEmployeesByCategory() {
//        TypedQuery<Employee> query = entityManager.createQuery(
//                "SELECT e FROM Employee e", Employee.class);
//
//        List<Employee> employees = query.getResultList();
//        return employees;
//    }

    public List<Employee> getAllEmployees() {
        TypedQuery<Employee> query = entityManager.createQuery(
                "SELECT e FROM Employee e", Employee.class);

        return query.getResultList();
    }

    public List<Employee> findAllDeptEmployee(Long departmentId) {
        String jpql = "SELECT e FROM Employee e WHERE e.department.id = :departmentId AND e.isDeleted = false";
        Query query = entityManager.createQuery(jpql, Employee.class);
        query.setParameter("departmentId", departmentId);
        return query.getResultList();
    }
}
