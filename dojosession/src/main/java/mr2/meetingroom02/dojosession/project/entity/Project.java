package mr2.meetingroom02.dojosession.project.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mr2.meetingroom02.dojosession.base.entity.BaseEntity;
import mr2.meetingroom02.dojosession.department.entity.Department;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Project extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private Area area;

    private String projectName;

    @ManyToOne
    @JoinColumn(name = "managed_department")
    private Department department;

}
