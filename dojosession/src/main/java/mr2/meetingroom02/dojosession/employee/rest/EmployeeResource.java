package mr2.meetingroom02.dojosession.employee.rest;

import mr2.meetingroom02.dojosession.base.exception.BadRequestException;
import mr2.meetingroom02.dojosession.base.exception.DuplicateException;
import mr2.meetingroom02.dojosession.base.exception.NotFoundException;
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
import java.net.URI;
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
//TODO: 401, 500, 403, 404 - create and test exceptions one by one, entity Not Found, bad request, internal server error
public class EmployeeResource {

    @Inject
    private EmployeeService employeeService;

    //TODO: Implement loggers
//    private static final Logger logger = LogManager.getLogger(EmployeeResource.class);

    @GET
    @Path("employees")
    public Response getEmployeesByCategory(@QueryParam("gender") String gender,
                                           @QueryParam("dept_id") Long departmentId,
                                           @QueryParam("page_number") Long pageNumber,
                                           @QueryParam("page_size") Long pageSize) throws NotFoundException {
        List<EmployeeResponseDTO> employeeList = employeeService.getEmployeesByCategory(gender, departmentId, pageNumber, pageSize);
        return Response.ok().entity(employeeList).build();
    }

    @POST
    @Path("employee")
    public Response addNewEmployee(@Valid EmployeeCreateRequestDTO employeeCreateRequestDTO) throws BadRequestException, NotFoundException, DuplicateException {
        EmployeeResponseDTO createdEmployee = employeeService.add(employeeCreateRequestDTO);
        return Response.created(URI.create("employees/" + createdEmployee.getId())).entity(createdEmployee).build();
    }

    @PUT
    @Path("employee/{id}")
    public Response updateEmployee(@Valid EmployeeUpdateRequestDTO dto,
                                   @PathParam("id") Long employeeId)
            throws BadRequestException, DuplicateException {
        EmployeeResponseDTO responseDTO = employeeService.update(employeeId, dto);
        return Response.ok().entity(responseDTO).build();
    }

    @DELETE
    @Path("/employee/{employeeId}")
    public Response deleteEmployee(@PathParam("employeeId") Long employeeId) throws NotFoundException {
        employeeService.remove(employeeId);
        return Response.noContent().build();
    }

}
