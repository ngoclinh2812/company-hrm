package mr2.meetingroom02.dojosession.base.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.validation.ValidationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidateExceptionMapper implements ExceptionMapper<ValidationException> {

    private static final Logger logger = LogManager.getLogger(InvalidateExceptionMapper.class);

    @Override
    public Response toResponse(ValidationException e) {
//        StackTraceElement[] stackTraceArray = e.getStackTrace();
//        for (StackTraceElement s : stackTraceArray) {
//            logger.error(s.getClassName());
//        }
//
//        ExceptionBody exceptionBody = e.getExceptionBOdy();
//
//        return Response.status(exceptionBody.getStatusCode())
//                .entity(exceptionBody)
//                .type(MediaType.APPLICATION_JSON)
//                .build();
        return null;
    }
}
