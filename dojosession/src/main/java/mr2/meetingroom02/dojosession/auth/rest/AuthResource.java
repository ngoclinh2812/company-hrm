package mr2.meetingroom02.dojosession.auth.rest;

import mr2.meetingroom02.dojosession.auth.model.JwtRequest;
import mr2.meetingroom02.dojosession.auth.model.JwtResponse;
import mr2.meetingroom02.dojosession.auth.utility.JwtUtils;
import mr2.meetingroom02.dojosession.base.exception.AuthorizedException;
import mr2.meetingroom02.dojosession.base.exception.InputValidationException;
import org.apache.maven.wagon.authorization.AuthorizationException;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/auth")
public class AuthResource {

    @Inject
    private JwtUtils jwtUtils;

    @POST
    @Path("/login")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response login(@Valid JwtRequest jwtRequest) throws AuthorizationException, InputValidationException, AuthorizedException {
            JwtResponse jwtResponse = jwtUtils.generateJwtResponse(jwtRequest);
        return Response.ok(jwtResponse).build();
    }
}
