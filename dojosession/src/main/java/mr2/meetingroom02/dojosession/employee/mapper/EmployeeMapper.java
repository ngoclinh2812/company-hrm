package mr2.meetingroom02.dojosession.employee.mapper;

import mr2.meetingroom02.dojosession.employee.dto.EmployeeResponseDTO;
import mr2.meetingroom02.dojosession.employee.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    EmployeeResponseDTO employeeToEmployeeDto(Employee employee);
    List<EmployeeResponseDTO> employeesToEmployeeDTOs(List<Employee> employees);

}
