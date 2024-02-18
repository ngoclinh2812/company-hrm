package mr2.meetingroom02.dojosession.employee.service;

import mr2.meetingroom02.dojosession.assignment.dao.AssignmentDAO;
import mr2.meetingroom02.dojosession.employee.dao.EmployeeDAO;
import mr2.meetingroom02.dojosession.employee.dto.EmployeeResponseDTO;
import mr2.meetingroom02.dojosession.employee.entity.Employee;
import mr2.meetingroom02.dojosession.employee.mapper.EmployeeMapper;
import mr2.meetingroom02.dojosession.project.entity.Project;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class EmployeeService {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private EmployeeDAO employeeDAO;

    @Inject
    private EmployeeMapper employeeMapper;

    @Inject
    private AssignmentDAO assignmentDAO;

    public List<EmployeeResponseDTO> getAllEmployees() {
        List<Employee> employees = employeeDAO.findAll();
        return employeeMapper.employeesToEmployeeDTOs(employees);
//        return employees;
    }

    public List<Project> getProjectsForEmployee(Long employeeId) {
        return assignmentDAO.getProjectsForEmployee(employeeId);
    }

//    public List<Project> getProjectsForEmployee(Long employeeId) {
//        Employee employee = employeeDAO.findById(employeeId).orElse(null);
//
//        if (employee != null) {
//            List<Assignment> assignments = employee.getAssignmentList();
//
//            return assignments.stream()
//                    .map(Assignment::getProject)
//                    .collect(Collectors.toList());
//        }
//        return List.of();
//    }

}
