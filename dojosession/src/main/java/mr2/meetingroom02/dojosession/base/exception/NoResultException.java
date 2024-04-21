package mr2.meetingroom02.dojosession.base.exception;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.ApplicationException;
import javax.ws.rs.core.Response;

@ApplicationException
@Getter
public class NoResultException extends GlobalException {

    private static final Logger logger = LogManager.getLogger(NoResultException.class);

    public NoResultException(String message) {
        super(Response.Status.NO_CONTENT.getStatusCode(), Response.Status.NO_CONTENT.getReasonPhrase(), message);
    }
}
