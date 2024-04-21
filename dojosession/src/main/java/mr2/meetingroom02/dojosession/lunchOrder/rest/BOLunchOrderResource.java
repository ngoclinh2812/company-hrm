package mr2.meetingroom02.dojosession.lunchOrder.rest;

import mr2.meetingroom02.dojosession.auth.utility.JwtUtils;
import mr2.meetingroom02.dojosession.base.exception.BadRequestException;
import mr2.meetingroom02.dojosession.base.exception.NoResultException;
import mr2.meetingroom02.dojosession.lunchOrder.dto.LunchOrderResponseDTO;
import mr2.meetingroom02.dojosession.lunchOrder.service.LunchOrderService;
import mr2.meetingroom02.dojosession.lunchSchedule.dto.CreateLunchOrderRequestDTO;
import mr2.meetingroom02.dojosession.lunchSchedule.dto.UpcomingWeekOrderDishesByDepartmentDTO;
import mr2.meetingroom02.dojosession.lunchSchedule.service.LunchScheduleService;
import org.apache.maven.wagon.authorization.AuthorizationException;

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

@Consumes({MediaType.APPLICATION_JSON})
@Path("/back-office/lunch-order")
public class BOLunchOrderResource {

    @Inject
    LunchOrderService lunchOrderService;

    @Inject
    JwtUtils jwtUtils;

    @GET
    @Path("/upcoming-week")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllMealsInUpcomingWeek() {
        List<UpcomingWeekOrderDishesByDepartmentDTO> responseDTOs = lunchOrderService.getMealsOfEachDepartmentInUpcomingWeek();
        return Response.ok().entity(responseDTOs).build();
    }

    @GET
    @Path("/upcoming-week/export-excel")
    @Produces({MediaType.APPLICATION_OCTET_STREAM})
    public Response ExportExcelMealsInUpcomingWeek() throws IOException, NoResultException {
        byte[] outputStream = lunchOrderService.exportExcelMealsInUpcomingWeek();
        return Response.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + String.format("aavn_lunch_schedule_" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + ".xlsx"))
                .entity(outputStream).build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public Response createOrder(
            @HeaderParam("Authorization") String authHeader,
            @Valid CreateLunchOrderRequestDTO createLunchOrderRequestDTOs) throws mr2.meetingroom02.dojosession.base.exception.NotFoundException, BadRequestException, AuthorizationException {
        String email = jwtUtils.getEmailFromToken(authHeader);
        LunchOrderResponseDTO lunchOrders = lunchOrderService.createLunchOrder(createLunchOrderRequestDTOs, email);
        return Response.created(URI.create("lunch-order")).entity(lunchOrders).build();
    }
}
