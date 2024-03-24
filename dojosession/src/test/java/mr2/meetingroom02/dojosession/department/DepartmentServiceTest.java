package mr2.meetingroom02.dojosession.department;

import mr2.meetingroom02.dojosession.department.service.DepartmentService;
import mr2.meetingroom02.dojosession.employee.dao.EmployeeDAO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private EmployeeDAO employeeDAO;

    @InjectMocks
    private DepartmentService departmentService;

    @Test
    public void getDeptEmployee_Successfully() {}

    @Test
    public void getEmptyDeptEmployee() {}



}