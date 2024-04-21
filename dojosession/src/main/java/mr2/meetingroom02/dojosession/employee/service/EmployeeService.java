package mr2.meetingroom02.dojosession.employee.service;

import mr2.meetingroom02.dojosession.base.exception.BadRequestException;
import mr2.meetingroom02.dojosession.base.exception.DuplicateException;
import mr2.meetingroom02.dojosession.base.exception.NotFoundException;
import mr2.meetingroom02.dojosession.department.dao.DepartmentDAO;
import mr2.meetingroom02.dojosession.department.entity.Department;
import mr2.meetingroom02.dojosession.employee.dao.EmployeeDAO;
import mr2.meetingroom02.dojosession.employee.dto.EmployeeCreateRequestDTO;
import mr2.meetingroom02.dojosession.employee.dto.EmployeeResponseDTO;
import mr2.meetingroom02.dojosession.employee.dto.EmployeeUpdateRequestDTO;
import mr2.meetingroom02.dojosession.employee.entity.Employee;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

import static mr2.meetingroom02.dojosession.department.constants.DepartmentExceptionMessages.DEPARTMENT_NOT_FOUND;
import static mr2.meetingroom02.dojosession.employee.constants.EmployeeExceptionMessage.*;

@Stateless
public class EmployeeService {

    @Inject
    private EmployeeDAO employeeDAO;

    @Inject
    private DepartmentDAO departmentDAO;

    @Inject
    private EmployeeMapper employeeMapper;

    public List<EmployeeResponseDTO> getEmployeesByCategory(String gender, Long departmentId, Long pageNumber, Long pageSize) throws NotFoundException {
        List<Employee> employees = employeeDAO.searchEmployeesByCategory(gender, departmentId, pageNumber.intValue(), pageSize.intValue());
        return employeeMapper.toEmployeeDTOList(employees);
    }

    public EmployeeResponseDTO add(EmployeeCreateRequestDTO employeeCreateRequestDTO) throws BadRequestException, NotFoundException, DuplicateException {

        Department department = departmentDAO.findById(employeeCreateRequestDTO.getDepartmentId()).orElseThrow(() -> new NotFoundException(DEPARTMENT_NOT_FOUND));

        isDuplicatedPhone(employeeCreateRequestDTO.getPhone());
        isDuplicatedEmail(employeeCreateRequestDTO.getEmail());
        isSalaryNegative(employeeCreateRequestDTO.getSalary());

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

        Employee savedEmp = employeeDAO.insert(newEmployee);

        return employeeMapper.toEmployeeDTO(savedEmp);

    }

    public EmployeeResponseDTO update(Long employeeId, EmployeeUpdateRequestDTO updateRequestDTO)
            throws BadRequestException, DuplicateException {
        Department department = departmentDAO.findById(updateRequestDTO.getDepartmentId())
                .orElseThrow(() -> new BadRequestException(DEPARTMENT_NOT_FOUND));
        Employee employee = employeeDAO.findById(employeeId)
                .orElseThrow(() -> new BadRequestException(EMPLOYEE_NOT_FOUND));

        isDuplicatedPhone(updateRequestDTO.getPhone());
        isDuplicatedEmail(updateRequestDTO.getEmail());
        isSalaryNegative(updateRequestDTO.getSalary());

        Employee updatedEmployee = employeeMapper.toUpdatesEntity(updateRequestDTO);
        Employee savedEmployee = employeeDAO.update(updatedEmployee);

        return employeeMapper.toEmployeeDTO(savedEmployee);
    }

    public void remove(Long employeeId) throws NotFoundException {
        Employee employee = employeeDAO.findById(employeeId)
                .orElseThrow(() -> new NotFoundException(EMPLOYEE_NOT_FOUND));
        employee.setIsDeleted(true);
        employeeDAO.update(employee);
    }

    private void isSalaryNegative(int salary) throws BadRequestException {
        if (salary < 0) {
            throw new BadRequestException(SALARY_IS_NEGATIVE);
        }
    }

    private void isDuplicatedPhone(String phone) throws DuplicateException {
        Employee employeeWithDuplicatedPhone = employeeDAO.findEmployeeByPhone(phone);
        if (employeeWithDuplicatedPhone != null) {
            throw new DuplicateException(DUPLICATED_PHONE);
        }
    }

    private void isDuplicatedEmail(String email) throws DuplicateException {
        Employee employeeWithDuplicatedEmail = employeeDAO.findEmployeeByEmail(email);
        if (employeeWithDuplicatedEmail != null) {
            throw new DuplicateException(DUPLICATED_EMAIL);
        }
    }

    public Employee getEmployeeByEmail(String email) {
        return employeeDAO.findEmployeeByEmail(email);
    }
}
