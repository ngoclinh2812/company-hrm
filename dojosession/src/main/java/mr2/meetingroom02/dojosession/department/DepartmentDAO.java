package mr2.meetingroom02.dojosession.department;

import mr2.meetingroom02.dojosession.base.dao.BaseDAO;
import mr2.meetingroom02.dojosession.department.entity.Department;

import javax.ejb.Stateless;

@Stateless
public class DepartmentDAO extends BaseDAO<Department>  {

    public DepartmentDAO() {
        super(Department.class);
    }




}
