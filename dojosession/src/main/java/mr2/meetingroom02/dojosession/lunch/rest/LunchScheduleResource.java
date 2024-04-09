package mr2.meetingroom02.dojosession.lunch.rest;

import mr2.meetingroom02.dojosession.base.exception.BadRequestException;
import mr2.meetingroom02.dojosession.base.exception.DuplicateException;
import mr2.meetingroom02.dojosession.base.exception.NotFoundException;
import mr2.meetingroom02.dojosession.lunch.dto.*;
import mr2.meetingroom02.dojosession.lunch.dto.response.MenuResponseDTO;
import mr2.meetingroom02.dojosession.lunch.service.LunchScheduleService;
import mr2.meetingroom02.dojosession.lunch.service.MenuService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("lunch-schedule")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class LunchScheduleResource {

    @Inject
    private LunchScheduleService lunchScheduleService;

    @Inject
    private MenuService menuService;

    @GET
    @Path("/{id}")
    public Response getLunchSchedule(@PathParam(value = "id") Long scheduleId) throws NotFoundException {
        LunchScheduleResponseDTO responseDTO = lunchScheduleService.getLunchScheduleById(scheduleId);
        return Response.ok().entity(responseDTO).build();
    }

    @POST
    @Path("/lunch-schedule")
    public Response createLunchMenu(@Valid CreateLunchScheduleDTO createLunchScheduleDTO) throws DuplicateException, BadRequestException {
        LunchScheduleResponseDTO responseDTO = lunchScheduleService.createLunchSchedule(createLunchScheduleDTO);
        return Response.created(URI.create("lunch/" + responseDTO.getId())).entity(responseDTO).build();
    }

    @POST
    @Path("/{lunchScheduleId}/menu")
    //TODO: Import list from excel file
    public Response createLunchMenu(@Valid CreateMenuRequestDTO createMenuRequestDTO,
                                    @PathParam("lunchScheduleId") Long lunchId
    ) throws DuplicateException, BadRequestException, NotFoundException {
        MenuResponseDTO responseDTO = menuService.createMenu(createMenuRequestDTO, lunchId);
        return Response.created(URI.create("lunch/" + responseDTO.getId())).entity(responseDTO).build();
    }

}
