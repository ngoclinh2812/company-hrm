package mr2.meetingroom02.dojosession.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import mr2.meetingroom02.dojosession.project.dto.ProjectResponseDTO;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class EmployeeProjectResponseDTO {
    private String name;
    private List<ProjectResponseDTO> projectList;
}
