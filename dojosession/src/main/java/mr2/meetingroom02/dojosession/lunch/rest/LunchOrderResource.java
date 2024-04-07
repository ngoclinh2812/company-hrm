package mr2.meetingroom02.dojosession.lunch.rest;

import mr2.meetingroom02.dojosession.lunch.dto.CreateLunchOrderRequestDTO;
import mr2.meetingroom02.dojosession.lunch.dto.LunchOrderResponseDTO;
import mr2.meetingroom02.dojosession.lunch.dto.UpcomingWeekMealsDTO;
import mr2.meetingroom02.dojosession.lunch.entity.LunchOrder;
import mr2.meetingroom02.dojosession.lunch.service.LunchOrderService;
import mr2.meetingroom02.dojosession.lunch.service.LunchScheduleService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("lunch-order")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class LunchOrderResource {

    @Inject
    LunchOrderService lunchOrderService;

    @Inject
    LunchScheduleService lunchScheduleService;

    @GET
    @Path("/lunch-orders/upcoming")
    public Response getAllMealsInUpcomingWeek() {
        List<UpcomingWeekMealsDTO> responseDTOs = lunchScheduleService.getMealsOfEachDepartmentInUpcomingWeek();
        return Response.ok().entity(responseDTOs).build();
    }

    @POST
    public Response createOrder(@Valid CreateLunchOrderRequestDTO createLunchOrderRequestDTOs) {
        LunchOrderResponseDTO lunchOrders = lunchOrderService.createLunchOrder(createLunchOrderRequestDTOs);
        return Response.created(URI.create("lunch-order")).entity(lunchOrders).build();
    }

}
