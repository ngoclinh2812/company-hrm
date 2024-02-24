package mr2.meetingroom02.dojosession.project.service;

import mr2.meetingroom02.dojosession.assignment.dao.AssignmentDAO;
import mr2.meetingroom02.dojosession.base.exception.ErrorMessage;
import mr2.meetingroom02.dojosession.base.exception.NotFoundException;
import mr2.meetingroom02.dojosession.employee.EmployeeDAO;
import mr2.meetingroom02.dojosession.employee.entity.Employee;
import mr2.meetingroom02.dojosession.project.dto.ProjectResponseDTO;
import mr2.meetingroom02.dojosession.project.entity.Project;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class ProjectService {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private EmployeeDAO employeeDAO;

    @Inject
    private AssignmentDAO assignmentDAO;

    @Inject
    private ProjectMapper projectMapper;

    public List<ProjectResponseDTO> getProjectsForEmployee(Long employeeId) throws NotFoundException {
        Employee employee = employeeDAO.findById(employeeId).orElseThrow(() -> new NotFoundException(ErrorMessage.EMPLOYEE_NOT_FOUND));
        List<Project> projects = assignmentDAO.getProjectsForEmployee(employeeId);
        return projectMapper.toProjectDTOs(projects);
    }
}
