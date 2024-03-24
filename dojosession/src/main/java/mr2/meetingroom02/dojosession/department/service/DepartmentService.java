package mr2.meetingroom02.dojosession.department.service;

import mr2.meetingroom02.dojosession.department.dao.DepartmentDAO;
import mr2.meetingroom02.dojosession.department.dto.DepartmentDTO;
import mr2.meetingroom02.dojosession.department.entity.Department;
import mr2.meetingroom02.dojosession.department.mapper.DepartmentMapper;
import mr2.meetingroom02.dojosession.employee.dao.EmployeeDAO;
import mr2.meetingroom02.dojosession.employee.dto.EmployeeResponseDTO;
import mr2.meetingroom02.dojosession.employee.entity.Employee;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class DepartmentService {

    @Inject
    private DepartmentDAO departmentDAO;

    @Inject
    private EmployeeDAO employeeDAO;

    @Inject
    private DepartmentMapper departmentMapper;

    public List<DepartmentDTO> getDepartmentList() {
        List<Department> departments = departmentDAO.findAll();
        return departments.stream()
                .map(d -> DepartmentDTO.builder()
                        .id(d.getId())
                        .name(d.getDepartmentName())
                        .build())
                .collect(Collectors.toList());
    }

    public List<EmployeeResponseDTO> getDeptEmployees(Long deptId) {
        List<Employee> employeeList = employeeDAO.findAllDeptEmployee(deptId);
        return employeeList.stream()
                .map(e -> EmployeeResponseDTO.builder()
                        .name(e.getFirstName() + " " + e.getMiddleName() + " " + e.getLastName())
                        .build()
                ).collect(Collectors.toList());
    }

    public DepartmentDTO addNewDepartment(DepartmentDTO departmentDTO) {
        Department department = departmentMapper.toDeptEntity(departmentDTO);
        return departmentMapper.toResponseDTO(departmentDAO.add(department));
    }
}
