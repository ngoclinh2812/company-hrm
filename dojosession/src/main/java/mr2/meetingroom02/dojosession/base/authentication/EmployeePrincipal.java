package mr2.meetingroom02.dojosession.base.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mr2.meetingroom02.dojosession.employee.entity.RoleEnum;

import java.security.Principal;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class EmployeePrincipal implements Principal {

    private String email;
    private RoleEnum role;

    @Override
    public String getName() {
        return null;
    }
}
