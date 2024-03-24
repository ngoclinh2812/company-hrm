package mr2.meetingroom02.dojosession.department.rest;

import mr2.meetingroom02.dojosession.department.dto.DepartmentDTO;
import mr2.meetingroom02.dojosession.department.service.DepartmentService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Produces({MediaType.APPLICATION_JSON})
public class DepartmentResource {

    @Inject
    private DepartmentService departmentService;

    @GET
    @Path("/departments")
    public Response getDepartmentList() {
        return Response.ok().entity(departmentService.getDepartmentList()).build();
    }

    @GET
    @Path("/department/{id}/employees")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response getDepartmentEmployeeList(@PathParam("id") Long id) {
        return Response.ok().entity(departmentService.getDeptEmployees(id)).build();
    }

    @POST
    @Path("/department")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createDepartment(@Valid DepartmentDTO departmentDTO) {
        return Response.ok().entity(departmentService.addNewDepartment(departmentDTO)).build();
    }
}
