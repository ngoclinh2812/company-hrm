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

import static mr2.meetingroom02.dojosession.base.exception.message.DepartmentExceptionMessage.DEPARTMENT_NOT_FOUND;
import static mr2.meetingroom02.dojosession.base.exception.message.EmployeeExceptionMessage.EMPLOYEE_NOT_FOUND;

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

    public List<EmployeeResponseDTO> getEmployeesByCategory(String gender, Long departmentId, Long pageNumber, Long pageSize) throws NotFoundException {
        List<Employee> employees = employeeDAO.searchEmployeesByCategory(gender, departmentId, pageNumber.intValue(), pageSize.intValue());
        return employeeMapper.toEmployeeDTOList(employees);
    }

    public EmployeeResponseDTO add(EmployeeCreateRequestDTO employeeCreateRequestDTO) throws BadRequestException, NotFoundException {

        Department department = departmentDAO.findById(employeeCreateRequestDTO.getDepartmentId()).orElseThrow(() -> new NotFoundException(DEPARTMENT_NOT_FOUND));
        boolean isPhoneDuplicated = isDuplicatedPhone(employeeCreateRequestDTO.getPhone());
        boolean isEmailDuplicated = isDuplicatedEmail(employeeCreateRequestDTO.getEmail());

        if (!isPhoneDuplicated && !isEmailDuplicated) {
            try {
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

            } catch (Exception e) {
                logger.error("Unsuccessful");
            }
        }

        return null;
    }

    private boolean isDuplicatedPhone(String phone) {
        Employee employeeWithDuplicatedPhone = employeeDAO.findEmployeeByPhone(phone);
        if (employeeWithDuplicatedPhone != null) {
            return false;
        }
        return true;
    }

    private boolean isDuplicatedEmail(String email) {
        Employee employeeWithDuplicatedEmail = employeeDAO.findEmployeeByEmail(email);
        if (employeeWithDuplicatedEmail != null) {
            return false;
        }
        return true;
    }

    public Employee update(EmployeeUpdateRequestDTO dto) throws BadRequestException {
        Employee employee = employeeDAO.findById(dto.getId()).orElseThrow(() -> new BadRequestException(EMPLOYEE_NOT_FOUND));
        Employee updatedEmployee = employeeMapper.toUpdatesEntity(dto);
        return employeeDAO.update(updatedEmployee);
    }

    public void remove(Long employeeId) throws NotFoundException {
        Employee employee = employeeDAO.findById(employeeId).orElseThrow(() -> new NotFoundException(EMPLOYEE_NOT_FOUND));
        employee.setIsDeleted(true);
        employeeDAO.update(employee);
    }
}
