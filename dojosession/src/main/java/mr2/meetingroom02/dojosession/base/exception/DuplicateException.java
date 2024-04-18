package mr2.meetingroom02.dojosession.base.exception;

import lombok.Getter;

import javax.ejb.ApplicationException;
import javax.ws.rs.core.Response;

@Getter
@ApplicationException
public class DuplicateException extends GlobalException {
    public DuplicateException(String message) {
        super(Response.Status.BAD_REQUEST.getStatusCode(), Response.Status.BAD_REQUEST.getReasonPhrase(), message);
    }
}
