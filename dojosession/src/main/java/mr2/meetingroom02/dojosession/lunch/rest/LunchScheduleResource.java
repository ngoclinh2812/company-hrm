package mr2.meetingroom02.dojosession.lunch.rest;

import mr2.meetingroom02.dojosession.lunch.dto.CreateLunchScheduleDTO;
import mr2.meetingroom02.dojosession.lunch.dto.LunchScheduleResponseDTO;
import mr2.meetingroom02.dojosession.lunch.service.LunchService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("lunch")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class LunchScheduleResource {

    @Inject
    private LunchService lunchService;

    @GET
    @Path("/{id}")
    public Response getLunchSchedule(@PathParam(value = "id") Long scheduleId) throws NotFoundException{
        LunchScheduleResponseDTO responseDTO = lunchService.getLunchScheduleById(scheduleId);
        return Response.ok().entity(responseDTO).build();
    }

    @POST
    public Response createLunchSchedule(@Valid CreateLunchScheduleDTO createLunchScheduleDTO) {
        LunchScheduleResponseDTO responseDTO = lunchService.createLunchSchedule(createLunchScheduleDTO);
        return Response.ok().entity(responseDTO).build();
    }

}
