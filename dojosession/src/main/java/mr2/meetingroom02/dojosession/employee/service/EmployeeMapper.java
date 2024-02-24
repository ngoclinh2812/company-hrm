package mr2.meetingroom02.dojosession.employee.service;

import mr2.meetingroom02.dojosession.employee.dto.EmployeeResponseDTO;
import mr2.meetingroom02.dojosession.employee.dto.EmployeeUpdateRequestDTO;
import mr2.meetingroom02.dojosession.employee.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "cdi"
)
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    EmployeeResponseDTO toEmployeeDTO(Employee employee);

    List<EmployeeResponseDTO> toEmployeeDTOList(List<Employee> employees);

    Employee toUpdatesEntity (EmployeeUpdateRequestDTO dto);
}
