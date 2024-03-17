package mr2.meetingroom02.dojosession.employee;

import mr2.meetingroom02.dojosession.base.dao.BaseDAO;
import mr2.meetingroom02.dojosession.base.exception.NotFoundException;
import mr2.meetingroom02.dojosession.department.DepartmentDAO;
import mr2.meetingroom02.dojosession.department.entity.Department;
import mr2.meetingroom02.dojosession.employee.entity.Employee;
import org.hibernate.Session;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

import static mr2.meetingroom02.dojosession.base.exception.message.DepartmentExceptionMessage.DEPARTMENT_NOT_FOUND;

@Stateless
public class EmployeeDAO extends BaseDAO<Employee> {

    public EmployeeDAO() {
        super(Employee.class);
    }

    @Inject
    private DepartmentDAO departmentDAO;

//    select * from employee e
//    where gender = 'Male'
//    and department_id = 7
//    order by salary desc limit 5 ;

    public List<Employee> searchEmployeesByCategory() throws NotFoundException {

        Department department = departmentDAO.findById(7).orElseThrow(() -> new NotFoundException(DEPARTMENT_NOT_FOUND));

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);

        Predicate predicateForGender
                = criteriaBuilder.equal(employeeRoot.get("gender"), "Male");

        Predicate predicateForDepartment = criteriaBuilder.equal(employeeRoot.get("department"), department);

        Predicate finalPredicate = criteriaBuilder.and(predicateForGender, predicateForDepartment);

        criteriaQuery.select(employeeRoot).where(finalPredicate);

        criteriaQuery.orderBy(criteriaBuilder.desc(employeeRoot.get("salary")));

        TypedQuery<Employee> query = entityManager.createQuery(criteriaQuery).setMaxResults(5);
        List<Employee> results = query.getResultList();
        return results;
    }

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
