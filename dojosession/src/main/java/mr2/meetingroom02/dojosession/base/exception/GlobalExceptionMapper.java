package mr2.meetingroom02.dojosession.base.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<GlobalException> {
    @Override
    public Response toResponse(GlobalException e) {
        return Response
                .status(e.getExceptionBody().getStatusCode())
                .entity(e.getExceptionBody())
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
