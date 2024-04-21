package mr2.meetingroom02.dojosession.auth.service;

import mr2.meetingroom02.dojosession.auth.model.JwtRequest;
import mr2.meetingroom02.dojosession.base.exception.InputValidationException;
import mr2.meetingroom02.dojosession.employee.entity.Employee;
import mr2.meetingroom02.dojosession.employee.service.EmployeeService;
import org.mindrot.jbcrypt.BCrypt;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Base64;

@Stateless
public class AuthService {

    @Inject
    private EmployeeService employeeService;

    public boolean checkAuthentication(JwtRequest jwtRequest) throws InputValidationException {
        Employee user = employeeService.getEmployeeByEmail(jwtRequest.getEmail());
        return BCrypt.checkpw(jwtRequest.getPassword(), user.getPassword());
    }

    private String decryptBase64Password(String password) throws InputValidationException {
        try {
            byte[] decodedBytes = Base64.getEncoder().encode(password.getBytes());
            return new String(decodedBytes);
        } catch (Exception e) {
            throw new InputValidationException("Invalid decoder" + e.getMessage());
        }
    }
}
