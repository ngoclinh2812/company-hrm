package mr2.meetingroom02.dojosession.lunch.rest;

import mr2.meetingroom02.dojosession.base.exception.BadRequestException;
import mr2.meetingroom02.dojosession.base.exception.DuplicateException;
import mr2.meetingroom02.dojosession.base.exception.NotFoundException;
import mr2.meetingroom02.dojosession.lunch.dto.LunchOrderDTO;
import mr2.meetingroom02.dojosession.lunch.dto.CreateLunchScheduleDTO;
import mr2.meetingroom02.dojosession.lunch.dto.LunchScheduleResponseDTO;
import mr2.meetingroom02.dojosession.lunch.entity.LunchOrder;
import mr2.meetingroom02.dojosession.lunch.service.LunchService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.time.LocalDate;

@Path("lunch")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class LunchScheduleResource {

    @Inject
    private LunchService lunchService;

    @GET
    @Path("/{id}")
    public Response getLunchSchedule(@PathParam(value = "id") Long scheduleId) throws NotFoundException {
        LunchScheduleResponseDTO responseDTO = lunchService.getLunchScheduleById(scheduleId);
        return Response.ok().entity(responseDTO).build();
    }

    @GET
    public Response getLunchScheduleByDate(@QueryParam("startDate") String startDate,
                                           @QueryParam("endDate") String endDate) {
        LocalDate parsedStartDate = LocalDate.parse(startDate);
        LocalDate parsedEndDate = LocalDate.parse(endDate);
        LunchScheduleResponseDTO responseDTO = lunchService.getLunchScheduleByStartDateOrEndDate(parsedStartDate, parsedEndDate);
        return Response.ok().entity(responseDTO).build();
    }

    @POST
    @Path("/lunch-schedule")
    public Response createLunchSchedule(@Valid CreateLunchScheduleDTO createLunchScheduleDTO) throws DuplicateException, BadRequestException {
        LunchScheduleResponseDTO responseDTO = lunchService.createLunchSchedule(createLunchScheduleDTO);
        return Response.created(URI.create("lunch/" + responseDTO.getId())).entity(responseDTO).build();
    }

    // TODO: create lunch order
    @POST
    @Path("/lunch-order")
    public Response orderLunch(@Valid LunchOrderDTO lunchOrderDTO) {
        LunchOrder lunchOrder = lunchService.createLunchOrder(lunchOrderDTO);
        return Response.created(URI.create("lunch/" + lunchOrder.getId())).entity(lunchOrder).build();
    }

}
