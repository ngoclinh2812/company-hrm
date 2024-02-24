package mr2.meetingroom02.dojosession.employee;

import mr2.meetingroom02.dojosession.employee.dto.EmployeeCreateRequestDTO;
import mr2.meetingroom02.dojosession.employee.dto.EmployeeResponseDTO;
import mr2.meetingroom02.dojosession.employee.dto.EmployeeUpdateRequestDTO;
import mr2.meetingroom02.dojosession.employee.service.EmployeeService;
import mr2.meetingroom02.dojosession.project.dto.ProjectResponseDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("employees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeResource {

    @Inject
    private EmployeeService employeeService;

    private static final Logger logger = LogManager.getLogger(EmployeeResource.class);

    @GET
    @Path("")
    public Response getAllEmployees() {
        logger.info("Attempting to get all employees");
        List<EmployeeResponseDTO> employeeList = employeeService.getAllEmployees();
        return Response.ok().entity(employeeList).build();
    }

    @GET
    @Path("/{employeeId}/projects")
    public Response getProjectsForEmployee(@PathParam("employeeId") Long employeeId) {
        List<ProjectResponseDTO> projects = employeeService.getProjectsForEmployee(employeeId);

        if (projects != null) {
            return Response.ok(projects).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Employee not found").build();
        }
    }

    @POST
    @Path("")
    public Response addNewEmployee(@Valid EmployeeCreateRequestDTO employeeCreateRequestDTO) {

        employeeService.add(employeeCreateRequestDTO);

        return Response.ok().entity(
                "Create employee successfully."
        ).build();
    }

    @PUT
    @Path("/{empId}")
    public Response updateEmployee(@Valid EmployeeUpdateRequestDTO dto) {
        employeeService.update(dto);
        return Response.ok().entity("Update employee successfully").build();
    }

    @DELETE
    @Path("/{employeeId}")
    public Response deleteEmployee(@PathParam("employeeId") Long employeeId) {
        employeeService.remove(employeeId);
        return Response.ok().entity("Remove employee successfully").build();
    }
}
