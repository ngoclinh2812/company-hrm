package mr2.meetingroom02.dojosession.department.rest;

//import mr2.meetingroom02.dojosession.department.service.DepartmentService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("departments")
public class DepartmentResource {
//    @Inject
//    private DepartmentService departmentService;

    @GET
    @Path("")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getDepartmentList() {
        return Response.ok().entity(List.of()).build();
    }
}
