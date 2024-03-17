package mr2.meetingroom02.dojosession.department;

import mr2.meetingroom02.dojosession.department.dto.DepartmentDTO;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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

    @POST
    public Response createDepartment(@Valid DepartmentDTO departmentDTO) {
        return Response.ok().entity(departmentService.addNewDepartment(departmentDTO)).build();
    }
}
