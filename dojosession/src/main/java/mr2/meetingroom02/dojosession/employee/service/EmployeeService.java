package mr2.meetingroom02.dojosession.employee.service;

import mr2.meetingroom02.dojosession.assignment.dao.AssignmentDAO;
import mr2.meetingroom02.dojosession.employee.dao.EmployeeDAO;
import mr2.meetingroom02.dojosession.employee.dto.EmployeeRequestDTO;
import mr2.meetingroom02.dojosession.employee.dto.EmployeeResponseDTO;
import mr2.meetingroom02.dojosession.employee.entity.Employee;
import mr2.meetingroom02.dojosession.project.dto.ProjectResponseDTO;
import mr2.meetingroom02.dojosession.project.entity.Project;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class EmployeeService {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private EmployeeDAO employeeDAO;

    @Inject
    private AssignmentDAO assignmentDAO;

    private static final Logger logger = LogManager.getLogger(EmployeeService.class);

    public List<EmployeeResponseDTO> getAllEmployees() {
        List<Employee> employees = employeeDAO.findAll();
        List<EmployeeResponseDTO> responseDTOList = employees.stream()
                .map(EmployeeResponseDTO::fromEntity)
                .collect(Collectors.toList());
        logger.info("Get all employees successfully {}", responseDTOList.stream().toList());
        return responseDTOList;
    }

    public List<ProjectResponseDTO> getProjectsForEmployee(Long employeeId) {
        List<Project> projects = assignmentDAO.getProjectsForEmployee(employeeId);
        List<ProjectResponseDTO> projectResponseDTOList = projects.stream()
                .map(ProjectResponseDTO::fromEntity)
                .collect(Collectors.toList());
        return projectResponseDTOList;
    }

    public void add(EmployeeRequestDTO employeeRequestDTO) {

        Employee newEmployee = Employee.builder()
                .dateOfBirth(employeeRequestDTO.getDateOfBirth())
                .gender(employeeRequestDTO.getGender())
                .salary(employeeRequestDTO.getSalary())
                .firstName(employeeRequestDTO.getFirstName())
                .middleName(employeeRequestDTO.getMiddleName())
                .lastName(employeeRequestDTO.getLastName())
                .build();

        employeeDAO.add(newEmployee);
    }

    public void remove(Long employeeId) {
        Optional<Employee> employee = employeeDAO.findById(employeeId);
        if (employee.isPresent()) {
            Employee employee1 = employee.get();
            employee1.setDeleted(true);
            employeeDAO.update(employee1);
        }
    }

    public void update(EmployeeRequestDTO employeeRequestDTO) {
        Long employeeId = employeeRequestDTO.getId(); // Assuming EmployeeRequestDTO has an id field
        Optional<Employee> optionalEmployee = employeeDAO.findById(employeeId);

        if (optionalEmployee != null) {

            Employee foundEmployee = optionalEmployee.get();

            foundEmployee.setDateOfBirth(employeeRequestDTO.getDateOfBirth());
            foundEmployee.setGender(employeeRequestDTO.getGender());
            foundEmployee.setSalary(employeeRequestDTO.getSalary());
            foundEmployee.setFirstName(employeeRequestDTO.getFirstName());
            foundEmployee.setMiddleName(employeeRequestDTO.getMiddleName());
            foundEmployee.setLastName(employeeRequestDTO.getLastName());

            employeeDAO.update(foundEmployee);
        } else {
            logger.error("Could not update employee info");
        }
    }
}
