package mr2.meetingroom02.dojosession.employee.service;

import mr2.meetingroom02.dojosession.assignment.dao.AssignmentDAO;
import mr2.meetingroom02.dojosession.department.DepartmentDAO;
import mr2.meetingroom02.dojosession.department.entity.Department;
import mr2.meetingroom02.dojosession.employee.EmployeeDAO;
import mr2.meetingroom02.dojosession.employee.dto.EmployeeCreateRequestDTO;
import mr2.meetingroom02.dojosession.employee.dto.EmployeeResponseDTO;
import mr2.meetingroom02.dojosession.employee.dto.EmployeeUpdateRequestDTO;
import mr2.meetingroom02.dojosession.employee.entity.Employee;
import mr2.meetingroom02.dojosession.project.dto.ProjectResponseDTO;
import mr2.meetingroom02.dojosession.project.entity.Project;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.Stateless;
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
    private AssignmentDAO assignmentDAO;

    @Inject
    private DepartmentDAO departmentDAO;

    @Inject
    private EmployeeMapper employeeMapper;

    private static final Logger logger = LogManager.getLogger(EmployeeService.class);

    public List<EmployeeResponseDTO> getAllEmployees() {
        List<Employee> employees = employeeDAO.getAllExceptDeleted();
        List<EmployeeResponseDTO> responseDTOList = employeeMapper.toListEmployeeDTO(employees);
        return responseDTOList;
    }

    public List<ProjectResponseDTO> getProjectsForEmployee(Long employeeId) {
        List<Project> projects = assignmentDAO.getProjectsForEmployee(employeeId);
        List<ProjectResponseDTO> projectResponseDTOList = projects.stream()
                .map(ProjectResponseDTO::fromEntity)
                .collect(Collectors.toList());
        return projectResponseDTOList;
    }

    public EmployeeResponseDTO add(EmployeeCreateRequestDTO employeeCreateRequestDTO) {

        Department department = departmentDAO.findById(employeeCreateRequestDTO.getDepartmentId()).orElseThrow();

        Employee newEmployee = Employee.builder()
                .dateOfBirth(employeeCreateRequestDTO.getDateOfBirth())
                .gender(employeeCreateRequestDTO.getGender())
                .salary(employeeCreateRequestDTO.getSalary())
                .firstName(employeeCreateRequestDTO.getFirstName())
                .middleName(employeeCreateRequestDTO.getMiddleName())
                .lastName(employeeCreateRequestDTO.getLastName())
                .email(employeeCreateRequestDTO.getEmail())
                .phone(employeeCreateRequestDTO.getPhone())
                .department(department)
                .isDeleted(false)
                .build();

        Employee savedEmp = employeeDAO.add(newEmployee);

        return employeeMapper.toEmployeeDTO(savedEmp);
    }

    public void update(EmployeeUpdateRequestDTO dto) {
        Employee employee = employeeDAO.findById(dto.getId()).orElseThrow();
        Employee updatedEmployee = employeeMapper.toUpdatesEntity(dto);
        employeeDAO.update(updatedEmployee);
    }

    public void remove(Long employeeId) {
        Employee employee = employeeDAO.findById(employeeId).orElseThrow();
        employee.setDeleted(true);
        employeeDAO.update(employee);
    }
}
