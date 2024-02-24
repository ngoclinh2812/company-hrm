package mr2.meetingroom02.dojosession.base.exception;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.ApplicationException;
import javax.ws.rs.core.Response;

@ApplicationException
@Getter
public class NotFoundException extends GlobalException{

    private static final Logger logger = LogManager.getLogger(NotFoundException.class);

    public NotFoundException(String message) {
        super(Response.Status.NOT_FOUND.getStatusCode(), Response.Status.NOT_FOUND.getReasonPhrase(), message);
        logger.error(ErrorMessage.EMPLOYEE_NOT_FOUND);
    }
}
