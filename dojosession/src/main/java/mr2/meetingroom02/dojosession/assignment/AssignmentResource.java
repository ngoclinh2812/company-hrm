package mr2.meetingroom02.dojosession.assignment;

import mr2.meetingroom02.dojosession.assignment.entity.Assignment;
import mr2.meetingroom02.dojosession.assignment.entity.CreateEmployeeAssignmentDTO;
import mr2.meetingroom02.dojosession.assignment.service.AssignmentService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("assignments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AssignmentResource {

    @Inject
    private AssignmentService assignmentService;

    //TODO: Assign assignments of a project to employees who have less than 1 project at the time being and number of task is under 10
    @POST
    public Response assignTaskToEmployee(@Valid CreateEmployeeAssignmentDTO dto) {
        assignmentService.assignTaskForEmployee(dto);
        return null;
    }
}
