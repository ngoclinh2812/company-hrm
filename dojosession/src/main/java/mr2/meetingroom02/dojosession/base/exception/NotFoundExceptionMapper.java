package mr2.meetingroom02.dojosession.base.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    private static final Logger logger = LogManager.getLogger(NotFoundExceptionMapper.class);

    @Override
    public Response toResponse(NotFoundException e) {
        StackTraceElement[] stackTraceArray = e.getStackTrace();
        for (StackTraceElement s : stackTraceArray) {
            logger.error(s.getClassName());
        }
        ExceptionBody exceptionBody = e.getExceptionBody();

        return Response.status(exceptionBody.getStatusCode())
                .entity(exceptionBody)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
