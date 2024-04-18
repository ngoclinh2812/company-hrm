package mr2.meetingroom02.dojosession.lunch.rest;

import mr2.meetingroom02.dojosession.auth.utility.JwtUtils;
import mr2.meetingroom02.dojosession.base.exception.BadRequestException;
import mr2.meetingroom02.dojosession.base.exception.NotFoundException;
import mr2.meetingroom02.dojosession.lunch.dto.CreateLunchOrderRequestDTO;
import mr2.meetingroom02.dojosession.lunch.dto.LunchOrderResponseDTO;
import mr2.meetingroom02.dojosession.lunch.dto.UpcomingWeekMealsDTO;
import mr2.meetingroom02.dojosession.lunch.service.LunchOrderService;
import mr2.meetingroom02.dojosession.lunch.service.LunchScheduleService;
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

    @GET
    @Path("/upcoming-week")
    @RolesAllowed({"ROLE_ADMIN"})
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllMealsInUpcomingWeek() {
        List<UpcomingWeekMealsDTO> responseDTOs = lunchScheduleService.getMealsOfEachDepartmentInUpcomingWeek();
        return Response.ok().entity(responseDTOs).build();
    }

    @GET
    @Path("/upcoming-week/export-excel")
    @RolesAllowed({"ROLE_ADMIN"})
    @Produces({MediaType.APPLICATION_OCTET_STREAM})
    public Response ExportExcelMealsInUpcomingWeek(
            @Context ContainerRequestContext requestContext
    ) throws IOException {
        byte[] outputStream = lunchScheduleService.exportExcelMealsInUpcomingWeek();
        return Response.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + String.format("aavn_lunch_schedule_" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + ".xlsx"))
                .entity(outputStream).build();
    }

    @POST
    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createOrder(
            @HeaderParam("Authorization") String authHeader,
            @Valid CreateLunchOrderRequestDTO createLunchOrderRequestDTOs) throws NotFoundException, BadRequestException, AuthorizationException {
        String email = jwtUtils.getEmailFromToken(authHeader);
        LunchOrderResponseDTO lunchOrders = lunchOrderService.createLunchOrder(createLunchOrderRequestDTOs, email);
        return Response.created(URI.create("lunch-order")).entity(lunchOrders).build();
    }

}
