package mr2.meetingroom02.dojosession.auth.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import mr2.meetingroom02.dojosession.auth.model.JwtRequest;
import mr2.meetingroom02.dojosession.auth.model.JwtResponse;
import mr2.meetingroom02.dojosession.auth.service.AuthService;
import mr2.meetingroom02.dojosession.base.authentication.AuthenticationConfiguration;
import mr2.meetingroom02.dojosession.base.exception.InputValidationException;
import mr2.meetingroom02.dojosession.base.exception.AuthorizedException;
import mr2.meetingroom02.dojosession.base.exception.message.EmployeeErrorMessage;
import mr2.meetingroom02.dojosession.employee.entity.Employee;
import mr2.meetingroom02.dojosession.employee.entity.RoleEnum;
import mr2.meetingroom02.dojosession.employee.entity.StatusEnum;
import mr2.meetingroom02.dojosession.employee.service.EmployeeService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.maven.wagon.authorization.AuthorizationException;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.*;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Stateless
public class JwtUtils {

    @Inject
    private EmployeeService employeeService;

    @Inject
    private AuthService authService;

    private static final String EMAIL = "EMAIL";

    private static final String ROLE = "ROLE";

    private static final String BEARER = "Bearer";

    private static final Validator validator =
            Validation.byDefaultProvider()
                    .configure()
                    .messageInterpolator(new ParameterMessageInterpolator())
                    .buildValidatorFactory()
                    .getValidator();

    public JwtResponse generateJwtResponse(@Valid JwtRequest jwtRequest) throws AuthorizationException, InputValidationException, AuthorizedException {
        verifyJwtRequest(jwtRequest);
        String email = jwtRequest.getEmail().trim();
        String token = generateToken(jwtRequest);
        Employee employee = employeeService.getEmployeeByEmail(email);
        RoleEnum roleEnum = employee.getRole();
        StatusEnum status = employee.getStatus();

        return new JwtResponse(token, email, roleEnum, status);
    }

    public String generateToken(JwtRequest jwtRequest) throws AuthorizationException, InputValidationException, AuthorizedException {
        if (!authService.checkAuthentication(jwtRequest)) {
            throw new InputValidationException("Email or password is invalid");
        }
        String token;
        String secretKey = AuthenticationConfiguration.getSecretKey();
        String issuer = AuthenticationConfiguration.getIssuer();
        int liveTime = AuthenticationConfiguration.getTimeToLive();
        RoleEnum roleEnum = employeeService.getEmployeeByEmail(jwtRequest.getEmail()).getRole();

        try {
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            token = JWT.create()
                    .withIssuer(issuer)
                    .withIssuedAt(new Date())
                    .withJWTId(UUID.randomUUID().toString())
                    .withClaim(EMAIL, jwtRequest.getEmail())
                    .withClaim(ROLE, String.valueOf(roleEnum))
                    .withExpiresAt(new Date(System.currentTimeMillis() + liveTime))
                    .sign(algorithm);
        } catch (JWTCreationException | IllegalArgumentException e) {
            throw new AuthorizedException(EmployeeErrorMessage.UNAUTHORIZED_USER);
        }
        return token;
    }

    private void verifyJwtRequest(JwtRequest jwtRequest) throws AuthorizationException {
        Set<ConstraintViolation<JwtRequest>> violations = validator.validate(jwtRequest);

        if (CollectionUtils.isNotEmpty(violations)) {
            throw new ConstraintViolationException(violations);
        }

        StatusEnum statusEnum = employeeService.getEmployeeByEmail(jwtRequest.getEmail()).getStatus();
        if (!isActive(statusEnum)) {
            throw new AuthorizationException("Access is forbidden");
        }
    }

    private boolean isActive(StatusEnum status) {
        return status.equals(StatusEnum.ACTIVE);
    }

    public RoleEnum getRoleFromToken(String authorization) {
        String token = authorization.substring(BEARER.length()).trim();
        DecodedJWT decodedJWT = JWT.decode(token);
        return RoleEnum.valueOf(decodedJWT.getClaim(ROLE).asString());
    }

    public String getEmailFromToken(String authorization) throws AuthorizationException {
        if (authorization != null) {
            String token = authorization.substring(BEARER.length()).trim();
            DecodedJWT decodedJWT = JWT.decode(token);
            return decodedJWT.getClaim(EMAIL).asString();
        }
        throw new AuthorizationException("Login is required");
    }

    public Date getExpireTokenTime(String authorization) {
        String token = authorization.substring(BEARER.length()).trim();
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getExpiresAt();
    }

    public void validateJwtToken(String token) throws AuthorizationException {
        if (token == null) {
            throw new AuthorizationException("Unathorized");
        }
        try {
            String secretKey = AuthenticationConfiguration.getSecretKey();
            String issuer = AuthenticationConfiguration.getIssuer();
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer).build();
            verifier.verify(token.substring(BEARER.length()).trim());
        } catch (JWTVerificationException | IllegalArgumentException e) {
            throw new AuthorizationException("Unauthorized");
        }
    }
}
