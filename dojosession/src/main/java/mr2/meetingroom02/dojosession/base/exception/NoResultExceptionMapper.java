package mr2.meetingroom02.dojosession.base.exception;

import mr2.meetingroom02.dojosession.base.exception.message.LoggingExceptionMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NoResultExceptionMapper implements ExceptionMapper<InputValidationException> {

    private static final Logger logger = LogManager.getLogger(InputValidationException.class);

    @Override
    public Response toResponse(InputValidationException e) {
        logger.error(LoggingExceptionMessage.getMessage(e));

        ExceptionBody body = new ExceptionBody(
                Response.Status.NO_CONTENT.getStatusCode(),
                Response.Status.NO_CONTENT.getReasonPhrase(),
                e.getMessage()
        );

        return Response
                .status(body.getStatusCode())
                .entity(body)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
