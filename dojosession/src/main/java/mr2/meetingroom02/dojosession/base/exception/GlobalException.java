package mr2.meetingroom02.dojosession.base.exception;

import lombok.Getter;

import javax.ejb.ApplicationException;

@Getter
@ApplicationException
public abstract class GlobalException extends Exception {

    private ExceptionBody exceptionBody;

    public GlobalException(int statusCode, String errorKey, String message) {
        super(message);
        this.exceptionBody = new ExceptionBody(statusCode, errorKey, message);
    }

}
