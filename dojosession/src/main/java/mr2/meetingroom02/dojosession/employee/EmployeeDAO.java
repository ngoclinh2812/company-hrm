package mr2.meetingroom02.dojosession.employee;

import mr2.meetingroom02.dojosession.base.dao.BaseDAO;
import mr2.meetingroom02.dojosession.base.exception.NotFoundException;
import mr2.meetingroom02.dojosession.department.DepartmentDAO;
import mr2.meetingroom02.dojosession.department.entity.Department;
import mr2.meetingroom02.dojosession.employee.entity.Employee;
import mr2.meetingroom02.dojosession.employee.entity.Gender;
import org.hibernate.Session;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
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

    public List<Employee> searchEmployeesByCategory(String gender, Long departmentId, int pageNumber, int pageSize) throws NotFoundException {

        Department department = departmentDAO.findById(departmentId).orElseThrow(() -> new NotFoundException(DEPARTMENT_NOT_FOUND));

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);

        List<Predicate> predicates = new ArrayList<>();

        if (gender != null) {
            predicates.add(criteriaBuilder.equal(employeeRoot.get("gender"), Gender.valueOf(gender)));
        }

        if (department != null) {
            predicates.add(criteriaBuilder.equal(employeeRoot.get("department"), department));
        }

        criteriaQuery.select(employeeRoot).where(predicates.toArray(new Predicate[0]));

        int firstResult = (pageNumber - 1) * pageSize;

        TypedQuery<Employee> query = entityManager.createQuery(criteriaQuery)
                .setFirstResult(firstResult)
                .setMaxResults(pageSize);

        List<Employee> employees = query.getResultList();
        return employees;
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

    public Employee findEmployeeByPhone(String phone) {
        TypedQuery<Employee> query  =  entityManager.createQuery("SELECT e FROM Employee e WHERE e.phone = :phone", Employee.class)
                .setParameter("phone", phone);
        return query.getSingleResult();
    }

    public Employee findEmployeeByEmail(String email) {
        TypedQuery<Employee> query  =  entityManager.createQuery("SELECT e FROM Employee e WHERE e.email = :email", Employee.class)
                .setParameter("email", email);
        return query.getSingleResult();
    }
}
