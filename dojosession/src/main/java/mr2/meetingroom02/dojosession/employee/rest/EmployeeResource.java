package mr2.meetingroom02.dojosession.employee.rest;

import mr2.meetingroom02.dojosession.employee.dto.EmployeeResponseDTO;
import mr2.meetingroom02.dojosession.employee.service.EmployeeService;
import mr2.meetingroom02.dojosession.project.entity.Project;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("employees")
public class EmployeeResource {

    @Inject
    private EmployeeService employeeService;

    private static final Logger logger = LogManager.getLogger(EmployeeResource.class);

    @GET
    @Path("")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllEmployees() {
        logger.info("Attempting to get all employees");
        List<EmployeeResponseDTO> employeeList = employeeService.getAllEmployees();
        return Response.ok().entity(employeeList).build();
    }

    @GET
    @Path("/{employeeId}/projects")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getProjectsForEmployee(@PathParam("employeeId") Long employeeId) {
        List<Project> projects = employeeService.getProjectsForEmployee(employeeId);

        if (projects != null) {
            return Response.ok(projects).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Employee not found").build();
        }
    }
}
