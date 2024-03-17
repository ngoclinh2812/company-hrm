package mr2.meetingroom02.dojosession.base.exception.message;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmployeeExceptionMessage {
    public static final String DUPLICATED_EMAIL = "This email already exists";

    public static final String DUPLICATED_PHONE = "This phone number already exists";

    public static final String EMPTY_EMAIL = "Email can not be null";

    public static final String INVALID_DATE_FORMAT = "Invalid Date Format";

    public static final String SALARY_IS_NEGATIVE = "Salary should not be a negative number";

    public static final String INVALID_SALARY = "Salary has to be a number";

    public static final String EMPLOYEE_NOT_FOUND = "Employee not found";

}
