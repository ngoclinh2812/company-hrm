package mr2.meetingroom02.dojosession.department.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class DepartmentDTO implements Serializable {
    private String name;
}
