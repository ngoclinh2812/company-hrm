package mr2.meetingroom02.dojosession.employee;

import mr2.meetingroom02.dojosession.employee.dto.EmployeeCreateRequestDTO;
import mr2.meetingroom02.dojosession.employee.dto.EmployeeResponseDTO;
import mr2.meetingroom02.dojosession.employee.dto.EmployeeUpdateRequestDTO;
import mr2.meetingroom02.dojosession.employee.service.EmployeeService;
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
//TODO: 401, 500, 403, 404 - create and test exceptions one by one, entity Not Found, bad request, internal server error
//TODO: Practice Query Param (paging)
public class EmployeeResource {

    @Inject
    private EmployeeService employeeService;

    private static final Logger logger = LogManager.getLogger(EmployeeResource.class);

    @GET
    public Response getAllEmployees() {
        logger.info("Attempting to get all employees");
        List<EmployeeResponseDTO> employeeList = employeeService.getAllEmployees();
        return Response.ok().entity(employeeList).build();
    }

    @POST
    @Path("")
    //TODO: bad request (?)
    public Response addNewEmployee(@Valid EmployeeCreateRequestDTO employeeCreateRequestDTO) {
        employeeService.add(employeeCreateRequestDTO);
        //TODO: return uri in header (reference in agile skills)
        //TODO: Add exception
        return Response.ok().entity(
                "Create employee successfully."
        ).build();
    }

    @PUT
    @Path("/{empId}")
    public Response updateEmployee(@Valid EmployeeUpdateRequestDTO dto) {
        return Response.ok().entity(employeeService.update(dto)).build();
    }

    @DELETE
    @Path("/{employeeId}")
    public Response deleteEmployee(@PathParam("employeeId") Long employeeId) {
        employeeService.remove(employeeId);
        return Response.noContent().build();
    }
}
