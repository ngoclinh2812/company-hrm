package mr2.meetingroom02.dojosession.base.authentication;

import lombok.SneakyThrows;
import mr2.meetingroom02.dojosession.auth.utility.JwtUtils;
import mr2.meetingroom02.dojosession.base.exception.AuthorizedException;
import mr2.meetingroom02.dojosession.employee.entity.RoleEnum;
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
        String path = request.getUriInfo().getPath();
        if(path.contains("/auth/login")){
            return;
        }

        if (isNotValidJwt(authHeader)) {
            throw new AuthorizedException("invalid header");
        }

        RoleEnum role = jwtUtils.getRoleFromToken(authHeader);
            String email = jwtUtils.getEmailFromToken(authHeader);

        if(checkRole(role, path)){
            SecurityContext sc = new RequestSecurityContext(new EmployeePrincipal(email, role));
            request.setSecurityContext(sc);
            return;
        } else {
            throw new AuthorizedException("Forbidden");
        }

    }

    private boolean checkRole(RoleEnum role, String path) {
        if(role == RoleEnum.ROLE_ADMIN){
            if(!path.contains("back-office")) {
                return false;
            };
            return true;
        } else{
            if(!path.contains("back-office")) {
                return true;
            };
            return false;
        }
    }

    private boolean isNotValidJwt(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
                return true;
        } else{
            try {
                jwtUtils.validateJwtToken(header);
                return false;
            } catch (AuthorizationException e) {
                return true;
            }
        }
    }
}
