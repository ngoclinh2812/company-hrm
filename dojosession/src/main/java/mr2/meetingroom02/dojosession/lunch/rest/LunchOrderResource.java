package mr2.meetingroom02.dojosession.lunch.rest;

import mr2.meetingroom02.dojosession.base.exception.BadRequestException;
import mr2.meetingroom02.dojosession.base.exception.NotFoundException;
import mr2.meetingroom02.dojosession.lunch.dto.CreateLunchOrderRequestDTO;
import mr2.meetingroom02.dojosession.lunch.dto.LunchOrderResponseDTO;
import mr2.meetingroom02.dojosession.lunch.dto.UpcomingWeekMealsDTO;
import mr2.meetingroom02.dojosession.lunch.service.LunchOrderService;
import mr2.meetingroom02.dojosession.lunch.service.LunchScheduleService;

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

@Consumes({MediaType.APPLICATION_JSON})
@Path("/lunch-orders")
public class LunchOrderResource {

    @Inject
    LunchOrderService lunchOrderService;

    @Inject
    LunchScheduleService lunchScheduleService;

    @GET
    @Path("/upcoming-week")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllMealsInUpcomingWeek() {
        List<UpcomingWeekMealsDTO> responseDTOs = lunchScheduleService.getMealsOfEachDepartmentInUpcomingWeek();
        return Response.ok().entity(responseDTOs).build();
    }

    @GET
    @Path("/upcoming-week/export-excel")
    @Produces({MediaType.APPLICATION_OCTET_STREAM})
    public Response ExportExcelMealsInUpcomingWeek() throws IOException {
        byte[] outputStream = lunchScheduleService.exportExcelMealsInUpcomingWeek();
        return Response.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + String.format("aavn_lunch_schedule_" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + ".xlsx"))
                .entity(outputStream).build();
    }

    @POST
    @RolesAllowed({"ROLE_ADMIN"})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createOrder(
            @Valid CreateLunchOrderRequestDTO createLunchOrderRequestDTOs) throws NotFoundException, BadRequestException {
        LunchOrderResponseDTO lunchOrders = lunchOrderService.createLunchOrder(createLunchOrderRequestDTOs);
        return Response.created(URI.create("lunch-order")).entity(lunchOrders).build();
    }

}
