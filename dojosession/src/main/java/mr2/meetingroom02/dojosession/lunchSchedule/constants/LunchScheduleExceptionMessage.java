package mr2.meetingroom02.dojosession.lunchSchedule.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LunchScheduleExceptionMessage {
    public static final String LUNCH_SCHEDULE_NOT_FOUND = "Lunch schedule not found";

    public static final String START_DATE_IS_BEFORE_CURRENT_DATE = "Start date of the schedule must be after the current date";

    public static final String START_DATE_IS_AFTER_END_DATE = "Start date of schedule must be before end date";

    public static final String ORDER_DEADLINE_MUST_BE_BEFORE_START_DATE = "Order deadline must be before start date";

    public static final String OVERLAP_LUNCH_SCHEDULE = "This lunch schedule is overlap with other ones";

}
