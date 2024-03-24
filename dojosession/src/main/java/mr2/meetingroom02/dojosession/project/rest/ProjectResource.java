package mr2.meetingroom02.dojosession.project.rest;

import mr2.meetingroom02.dojosession.base.exception.NotFoundException;
import mr2.meetingroom02.dojosession.project.dto.ProjectResponseDTO;
import mr2.meetingroom02.dojosession.project.service.ProjectService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("projects")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProjectResource {

    @Inject
    private ProjectService projectService;

    @GET
    @Path("/{employeeId}/projects")
    public Response getProjectsForEmployee(@PathParam("employeeId") Long employeeId)
            throws javax.ws.rs.NotFoundException, NotFoundException {
        List<ProjectResponseDTO> projects = projectService.getProjectsForEmployee(employeeId);
        return Response.ok(projects).build();
    }
}


