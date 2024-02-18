package mr2.meetingroom02.dojosession.employee.dao;

import mr2.meetingroom02.dojosession.employee.entity.Employee;
import mr2.meetingroom02.dojosession.base.dao.BaseDAO;

import javax.ejb.Stateless;

@Stateless
public class EmployeeDAO extends BaseDAO<Employee> {
    public EmployeeDAO() {
        super(Employee.class);
    }

}
