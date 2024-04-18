package mr2.meetingroom02.dojosession.lunchSchedule;

import mr2.meetingroom02.dojosession.base.exception.NotFoundException;
import mr2.meetingroom02.dojosession.lunchSchedule.dao.LunchScheduleDAO;
import mr2.meetingroom02.dojosession.lunchSchedule.dto.LunchScheduleResponseDTO;
import mr2.meetingroom02.dojosession.lunchSchedule.service.LunchScheduleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LunchScheduleServiceTest {

    @Mock
    private LunchScheduleDAO lunchScheduleDAO;

    @InjectMocks
    private LunchScheduleService lunchScheduleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void getLunchScheduleById_ReturnSuccessfully() throws NotFoundException {
        when(lunchScheduleDAO.findById(1L)).thenReturn(null);

        LunchScheduleResponseDTO result = lunchScheduleService.getLunchScheduleById(1L);

        assertNotNull(result);
    }

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
