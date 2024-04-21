package mr2.meetingroom02.dojosession.lunchOrder.rest;

import mr2.meetingroom02.dojosession.auth.utility.JwtUtils;
import mr2.meetingroom02.dojosession.base.exception.BadRequestException;
import mr2.meetingroom02.dojosession.base.exception.NotFoundException;
import mr2.meetingroom02.dojosession.lunchOrder.service.LunchOrderService;
import mr2.meetingroom02.dojosession.lunchOrder.dto.LunchOrderResponseDTO;
import mr2.meetingroom02.dojosession.lunchSchedule.dto.CreateLunchOrderRequestDTO;
import mr2.meetingroom02.dojosession.lunchSchedule.dto.UpcomingWeekOrderDishesByDepartmentDTO;
import mr2.meetingroom02.dojosession.lunchSchedule.service.LunchScheduleService;
import org.apache.maven.wagon.authorization.AuthorizationException;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;

@Consumes({MediaType.APPLICATION_JSON})
@Path("/lunch-order")
public class LunchOrderResource {

    @Inject
    LunchOrderService lunchOrderService;

    @Inject
    LunchScheduleService lunchScheduleService;

    @Inject
    JwtUtils jwtUtils;

    //TODO: API - Get upcoming week orders of the authorized employee

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public Response createOrder(
            @HeaderParam("Authorization") String authHeader,
            @Valid CreateLunchOrderRequestDTO createLunchOrderRequestDTOs) throws NotFoundException, BadRequestException, AuthorizationException {
        String email = jwtUtils.getEmailFromToken(authHeader);
        LunchOrderResponseDTO lunchOrders = lunchOrderService.createLunchOrder(createLunchOrderRequestDTOs, email);
        return Response.created(URI.create("lunch-order")).entity(lunchOrders).build();
    }

}
