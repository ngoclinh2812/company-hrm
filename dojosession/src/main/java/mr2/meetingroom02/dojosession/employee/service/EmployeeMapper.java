package mr2.meetingroom02.dojosession.employee.service;

import mr2.meetingroom02.dojosession.employee.dto.EmployeeResponseDTO;
import mr2.meetingroom02.dojosession.employee.dto.EmployeeUpdateRequestDTO;
import mr2.meetingroom02.dojosession.employee.entity.Employee;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "cdi"
)
public interface EmployeeMapper {

    @Mappings({
            @Mapping(target = "name", ignore = true)
    })
    EmployeeResponseDTO toEmployeeDTO(Employee employee);

    @Mappings({
            @Mapping(target = "name", ignore = true)
    })
    List<EmployeeResponseDTO> toEmployeeDTOList(List<Employee> employees);

    Employee toUpdatesEntity (EmployeeUpdateRequestDTO dto);

    @AfterMapping
    default void setName(@MappingTarget EmployeeResponseDTO employeeDTO, Employee employee) {
        String fullName = employee.getFirstName() + " " +
                (employee.getMiddleName() != null ? employee.getMiddleName() + " " : "") +
                employee.getLastName();
        employeeDTO.setName(fullName);
    }

}
