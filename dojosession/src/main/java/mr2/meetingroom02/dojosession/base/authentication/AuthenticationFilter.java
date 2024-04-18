package mr2.meetingroom02.dojosession.base.authentication;

import lombok.SneakyThrows;
import mr2.meetingroom02.dojosession.auth.utility.JwtUtils;
import mr2.meetingroom02.dojosession.base.exception.AuthorizedException;
import mr2.meetingroom02.dojosession.base.exception.message.EmployeeErrorMessage;
import mr2.meetingroom02.dojosession.employee.entity.RoleEnum;
import org.apache.logging.log4j.ThreadContext;
import org.apache.maven.wagon.authorization.AuthorizationException;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.util.*;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    @Context
    private ResourceInfo info;

    @Inject
    private JwtUtils jwtUtils;

    private static final Map<RoleEnum, List<String>> allowedEndpoints = new HashMap<>();

    static {
        allowedEndpoints.put(RoleEnum.ROLE_ADMIN, Arrays.asList("/lunch-order/upcoming-week/export-excel"));
        allowedEndpoints.put(RoleEnum.ROLE_USER, Arrays.asList("/lunch-order/upcoming-week"));
    }

    @SneakyThrows
    @Override
    public void filter(ContainerRequestContext request) {

        String authHeader = request.getHeaderString("Authorization");
        if (isNotValidJwt(authHeader)) {
            SecurityContext sc = new RequestSecurityContext(new EmployeePrincipal());
            request.setSecurityContext(sc);
            return;
        }

        RoleEnum role = jwtUtils.getRoleFromToken(authHeader);
        String email = jwtUtils.getEmailFromToken(authHeader);
        SecurityContext sc = new RequestSecurityContext(new EmployeePrincipal(email, role));

        String[] localPart = email.split("@");
        ThreadContext.put("mail", localPart[0] + ":" + UUID.randomUUID().toString().replace("-", "").substring(0, 8));

        request.setSecurityContext(sc);

//        String authHeader = request.getHeaderString("Authorization");
//
//        if (isNotValidJwt(authHeader)) {
//            SecurityContext sc = new RequestSecurityContext(new EmployeePrincipal());
//            request.setSecurityContext(sc);
//            return;
//        }
//
//        RoleEnum role = jwtUtils.getRoleFromToken(authHeader);
//
//        String path = request.getUriInfo().getPath();
//
//        String[] allowedRoles = API_ROLES_CONFIG.get(path);
//
//        if (allowedRoles != null && !Arrays.asList(allowedRoles).contains(role.toString())) {
//            throw new AuthorizedException(EmployeeErrorMessage.UNAUTHORIZED_USER);
//        }
//
//        String email = null;
//
//        try {
//            email = jwtUtils.getEmailFromToken(authHeader);
//        } catch (AuthorizationException e) {
//            throw new RuntimeException(e);
//        }
//
//        SecurityContext sc = new RequestSecurityContext(new EmployeePrincipal(email, role));
//
//        String[] localPart = email.split("@");
//        ThreadContext.put("mail", localPart[0] + ":" + UUID.randomUUID().toString().replace("-", "").substring(0, 8));
//
//        request.setSecurityContext(sc);
    }

    private boolean isNotValidJwt(String header) {
        if (header == null) {
                return true;
        }

        try {
            jwtUtils.validateJwtToken(header);
        } catch (AuthorizationException e) {
            return true;
        }

        return !header.startsWith("Bearer ");
    }
}
