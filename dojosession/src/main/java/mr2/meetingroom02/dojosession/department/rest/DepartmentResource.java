package mr2.meetingroom02.dojosession.department.rest;

//import mr2.meetingroom02.dojosession.department.service.DepartmentService;

import mr2.meetingroom02.dojosession.department.service.DepartmentService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("departments")
@Produces({MediaType.APPLICATION_JSON})
public class DepartmentResource {

    @Inject
    private DepartmentService departmentService;

    @GET
    @Path("")
    public Response getDepartmentList() {
        return Response.ok().entity(departmentService.getDepartmentList()).build();
    }

    @GET
    @Path("/{id}/employees")
    public Response getDepartmentEmployeeList(@PathParam("id") Long id) {
        return Response.ok().entity(departmentService.getDeptEmployees(id)).build();
    }
}
