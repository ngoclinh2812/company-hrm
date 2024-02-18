package mr2.meetingroom02.dojosession.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import mr2.meetingroom02.dojosession.project.entity.Project;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ProjectResponseDTO {
    private String projectName;

    public static ProjectResponseDTO fromEntity(Project project) {
        return ProjectResponseDTO.builder()
                .projectName(project.getProjectName())
                .build();
    }
}
