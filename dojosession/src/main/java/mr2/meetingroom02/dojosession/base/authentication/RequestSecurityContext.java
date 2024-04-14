package mr2.meetingroom02.dojosession.base.authentication;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

public class RequestSecurityContext implements SecurityContext {

    private final EmployeePrincipal employeePrincipal;

    public RequestSecurityContext(EmployeePrincipal employeePrincipal) {
        this.employeePrincipal = employeePrincipal;
    }

    @Override
    public Principal getUserPrincipal() {
        return employeePrincipal;
    }

    @Override
    public boolean isUserInRole(String s) {
        return employeePrincipal.getRole().name().equals(s);
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public String getAuthenticationScheme() {
        return SecurityContext.BASIC_AUTH;
    }
}