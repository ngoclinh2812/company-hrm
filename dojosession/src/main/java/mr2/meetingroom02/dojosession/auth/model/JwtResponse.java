package mr2.meetingroom02.dojosession.auth.model;

import lombok.*;
import mr2.meetingroom02.dojosession.employee.entity.RoleEnum;
import mr2.meetingroom02.dojosession.employee.entity.StatusEnum;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class JwtResponse {
    private String token;
    private String email;

    private RoleEnum role;

    private String type = "Bearer";
    private StatusEnum status;

    public JwtResponse(String token, String email, RoleEnum role, StatusEnum status) {
        this.token = token;
        this.email = email;
        this.role = role;
        this.status = status;
    }
}
