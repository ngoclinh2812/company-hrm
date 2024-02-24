package mr2.meetingroom02.dojosession.project.service;

import mr2.meetingroom02.dojosession.project.dto.ProjectResponseDTO;
import mr2.meetingroom02.dojosession.project.entity.Project;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface ProjectMapper {

    List<ProjectResponseDTO> toProjectDTOs(List<Project> projects);

}
