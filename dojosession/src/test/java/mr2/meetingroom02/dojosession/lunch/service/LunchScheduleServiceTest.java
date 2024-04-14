package mr2.meetingroom02.dojosession.lunch.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LunchScheduleServiceTest {

    @Test
    void getLunchScheduleById_ReturnSuccessfully() {}

    @Test
    void getLunchScheduleNextWeek_AvailableLunchScheduleNextWeek_ReturnLunchScheduleDTO() {}

    @Test
    void getLunchScheduleNextWeek_EmptyLunchScheduleNextWeek_ReturnEmptyObject() {}

    @Test
    void createLunchSchedule_MissingStartDateOrEndDateOrOrderDeadline_ThrowBadRequest() {}

    @Test
    void createLunchSchedule_EndDateBeforeStartDate_ThrowBadRequest() {}

    @Test
    void createLunchSchedule_OrderDeadlineAfterStartDate_ThrowBadRequest() {}

    @Test
    void createLunchSchedule_OverlappingDateWithOtherSchedules_ThrowBadRequest() {}

    @Test
    void createLunchSchedule_StartDateEqualEndDate_ReturnSuccessfully() {}

}
