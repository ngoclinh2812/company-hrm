package mr2.meetingroom02.dojosession.employee.service;

import mr2.meetingroom02.dojosession.base.exception.BadRequestException;
import mr2.meetingroom02.dojosession.base.exception.NotFoundException;
import mr2.meetingroom02.dojosession.department.DepartmentDAO;
import mr2.meetingroom02.dojosession.department.entity.Department;
import mr2.meetingroom02.dojosession.employee.EmployeeDAO;
import mr2.meetingroom02.dojosession.employee.dto.EmployeeCreateRequestDTO;
import mr2.meetingroom02.dojosession.employee.dto.EmployeeResponseDTO;
import mr2.meetingroom02.dojosession.employee.dto.EmployeeUpdateRequestDTO;
import mr2.meetingroom02.dojosession.employee.entity.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

import static mr2.meetingroom02.dojosession.base.exception.message.DepartmentExceptionMessage.DEPARTMENT_NOT_FOUND;
import static mr2.meetingroom02.dojosession.employee.dto.EmployeeResponseDTO.fromEntity;

@Stateless
public class EmployeeService {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private EmployeeDAO employeeDAO;

    @Inject
    private DepartmentDAO departmentDAO;

    @Inject
    private EmployeeMapper employeeMapper;

    private static final Logger logger = LogManager.getLogger(EmployeeService.class);

    public List<EmployeeResponseDTO> getAllEmployees() throws NotFoundException {
        List<Employee> employees = employeeDAO.searchEmployeesByCategory();
        return employeeMapper.toEmployeeDTOList(employees);
    }

    public EmployeeResponseDTO add(EmployeeCreateRequestDTO employeeCreateRequestDTO) throws BadRequestException, NotFoundException {

        Department department = departmentDAO.findById(employeeCreateRequestDTO.getDepartmentId()).orElseThrow(() -> new NotFoundException(DEPARTMENT_NOT_FOUND));

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

    public Employee update(EmployeeUpdateRequestDTO dto) throws BadRequestException {
        Employee employee = employeeDAO.findById(dto.getId()).orElseThrow();
        Employee updatedEmployee = employeeMapper.toUpdatesEntity(dto);
        return employeeDAO.update(updatedEmployee);
    }

    public void remove(Long employeeId) {
        Employee employee = employeeDAO.findById(employeeId).orElseThrow(); //TODO:L Throw exception not found, not null
        employee.setIsDeleted(true);
        employeeDAO.update(employee);
    }
}
