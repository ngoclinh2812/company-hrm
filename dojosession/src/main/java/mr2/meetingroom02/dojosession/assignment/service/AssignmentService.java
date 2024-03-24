package mr2.meetingroom02.dojosession.assignment.service;

import mr2.meetingroom02.dojosession.assignment.dao.AssignmentDAO;
import mr2.meetingroom02.dojosession.assignment.entity.CreateEmployeeAssignmentDTO;
import mr2.meetingroom02.dojosession.employee.dao.EmployeeDAO;
import mr2.meetingroom02.dojosession.employee.entity.Employee;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class AssignmentService {

    @Inject
    private AssignmentDAO assignmentDAO;

    @Inject
    private EmployeeDAO employeeDAO;

    public void assignTaskForEmployee(CreateEmployeeAssignmentDTO dto) {
        // check for employees who currently have less than 2 projects and prioritize those who haven't joined any project

//        select e.id, concat(e.first_name, e.middle_name, e.last_name) as full_name,
//        p.project_name, a.created_at, count(p.id) as number_of_projects, a.status as status from employee e
//        right join "assignment" a on e.id = a.employee_id
//        left join project p on a.project_id = p.id
//        where a.status = 1
//        group by (p.id), e.id, a.id
//        order by number_of_projects desc NULLS first;
        
        List<Employee> notBusyEmployee = employeeDAO.getEmployeesWithNumberOfCurrentProjectLessThan(2);

    }
}
