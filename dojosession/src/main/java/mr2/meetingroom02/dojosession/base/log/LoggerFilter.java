package mr2.meetingroom02.dojosession.base.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
@Priority(1)
public class LoggerFilter implements ContainerRequestFilter {

    private static final Logger logger = LogManager.getLogger(LoggerFilter.class);

    @Context
    HttpServletRequest httpRequest;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String ip = httpRequest.getRemoteAddr();
        String path = requestContext.getUriInfo().getAbsolutePath().getPath();
        String method = requestContext.getMethod();
        String message = String.format("%s:%s:%s", ip, method, path);

        logger.info(message);
    }
}
