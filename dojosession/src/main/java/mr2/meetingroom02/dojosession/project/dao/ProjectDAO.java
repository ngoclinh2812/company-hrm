package mr2.meetingroom02.dojosession.project.dao;

import mr2.meetingroom02.dojosession.base.dao.BaseDAO;
import mr2.meetingroom02.dojosession.base.entity.BaseEntity;
import mr2.meetingroom02.dojosession.project.entity.Project;

import javax.ejb.Stateless;

@Stateless
public class ProjectDAO extends BaseDAO<Project> {

    public ProjectDAO() {
        super(Project.class);
    }

}
