package mr2.meetingroom02.dojosession.base.exception;

import mr2.meetingroom02.dojosession.base.exception.message.LoggingExceptionMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<GlobalException> {

    private static Logger logger = LogManager.getLogger(GlobalException.class);

    @Override
    public Response toResponse(GlobalException e) {
        logger.error(LoggingExceptionMessage.getMessage(e));
        return Response
                .status(e.getExceptionBody().getStatusCode())
                .entity(e.getExceptionBody())
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
