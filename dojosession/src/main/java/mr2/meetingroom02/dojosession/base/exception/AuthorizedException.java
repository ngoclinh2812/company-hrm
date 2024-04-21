package mr2.meetingroom02.dojosession.base.exception;

import lombok.Getter;
import mr2.meetingroom02.dojosession.auth.constants.AuthExceptionMessages;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.ApplicationException;
import javax.ws.rs.core.Response;

@ApplicationException
@Getter
public class AuthorizedException extends GlobalException  {

    private static final Logger logger = LogManager.getLogger(NotFoundException.class);

    public AuthorizedException(String message) {
        super(Response.Status.UNAUTHORIZED.getStatusCode(), Response.Status.UNAUTHORIZED.getReasonPhrase(), message);
        logger.error(AuthExceptionMessages.UNAUTHORIZED_USER);
    }
}
