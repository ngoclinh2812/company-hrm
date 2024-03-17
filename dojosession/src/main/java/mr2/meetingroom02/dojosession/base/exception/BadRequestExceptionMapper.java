package mr2.meetingroom02.dojosession.base.exception;

import mr2.meetingroom02.dojosession.base.exception.message.LoggingExceptionMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<BadRequestException> {

    private static final Logger logger = LogManager.getLogger(BadRequestException.class);

    @Override
    public Response toResponse(BadRequestException e) {
        logger.error(LoggingExceptionMessage.getMessage(e));

        ExceptionBody body = new ExceptionBody(
                Response.Status.BAD_REQUEST.getStatusCode(),
                Response.Status.BAD_REQUEST.getReasonPhrase(),
                null
        );

        return Response
                .status(body.getStatusCode())
                .entity(body)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
