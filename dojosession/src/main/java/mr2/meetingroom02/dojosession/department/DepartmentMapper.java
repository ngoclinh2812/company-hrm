package mr2.meetingroom02.dojosession.department;

import mr2.meetingroom02.dojosession.department.dto.DepartmentDTO;
import mr2.meetingroom02.dojosession.department.entity.Department;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface DepartmentMapper {

    Department toDeptEntity(DepartmentDTO dto);

    DepartmentDTO toResponseDTO(Department add);
}
