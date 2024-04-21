package mr2.meetingroom02.dojosession.lunchSchedule;

import mr2.meetingroom02.dojosession.base.exception.BadRequestException;
import mr2.meetingroom02.dojosession.base.exception.NotFoundException;
import mr2.meetingroom02.dojosession.lunchSchedule.dao.LunchScheduleDAO;
import mr2.meetingroom02.dojosession.lunchSchedule.dto.CreateLunchOrderRequestDTO;
import mr2.meetingroom02.dojosession.lunchSchedule.dto.CreateLunchScheduleDTO;
import mr2.meetingroom02.dojosession.lunchSchedule.dto.LunchScheduleResponseDTO;
import mr2.meetingroom02.dojosession.lunchSchedule.entity.LunchSchedule;
import mr2.meetingroom02.dojosession.lunchSchedule.mapper.LunchScheduleMapper;
import mr2.meetingroom02.dojosession.lunchSchedule.service.LunchScheduleService;
import mr2.meetingroom02.dojosession.menu.dao.MenuDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static mr2.meetingroom02.dojosession.base.exception.message.LunchScheduleExceptionMessage.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LunchScheduleServiceTest {

    @Mock
    private LunchScheduleDAO lunchScheduleDAO;

    @Mock
    private LunchScheduleMapper lunchScheduleMapper;

    @Mock
    private MenuDAO menuDAO;

    @InjectMocks
    private LunchScheduleService lunchScheduleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getLunchScheduleById_ReturnSuccessfully() throws NotFoundException {
        LunchSchedule lunchSchedule = LunchSchedule.builder()
                .build();

        LunchScheduleResponseDTO responseDTO = LunchScheduleResponseDTO.builder().build();

        when(lunchScheduleDAO.getScheduleLunch(1L)).thenReturn(lunchSchedule);
        when(lunchScheduleMapper.toLunchScheduleDTO(lunchSchedule)).thenReturn(responseDTO);

        LunchScheduleResponseDTO result = lunchScheduleService.getLunchScheduleById(1L);

        assertNotNull(result);
    }

    @Test
    void createLunchSchedule_StartDateAfterEndDate_ThrowBadRequest() {
        CreateLunchScheduleDTO scheduleDTO = CreateLunchScheduleDTO.builder()
                .startDate(LocalDate.of(2024, 12, 1))
                .endDate(LocalDate.of(2024, 11, 30))
                .orderDeadline(LocalDate.of(2024, 11, 30))
                .build();

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            lunchScheduleService.createLunchSchedule(scheduleDTO);
        });

        assertEquals(START_DATE_IS_AFTER_END_DATE, exception.getMessage());
    }

    @Test
    void createLunchSchedule_OrderDeadlineAfterStartDate_ThrowBadRequest() {
        LocalDate currentDate = LocalDate.now();
        CreateLunchScheduleDTO scheduleDTO = CreateLunchScheduleDTO.builder()
                .startDate(currentDate)
                .endDate(currentDate.plusDays(1))
                .orderDeadline(currentDate)
                .build();

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            lunchScheduleService.createLunchSchedule(scheduleDTO);
        });

        assertEquals(ORDER_DEADLINE_MUST_BE_BEFORE_START_DATE, exception.getMessage());

    }

    @Test
    void createLunchSchedule_OverlappingDateWithOtherSchedules_ThrowBadRequest() {
        CreateLunchScheduleDTO scheduleDTO = CreateLunchScheduleDTO.builder()
                .startDate(LocalDate.of(2024, 12, 2))
                .endDate(LocalDate.of(2024, 12, 2))
                .orderDeadline(LocalDate.of(2024, 11, 30))
                .build();

        List<LunchSchedule> duplicatedSchedules = new ArrayList<>();
        duplicatedSchedules.add(LunchSchedule.builder()
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(1))
                .orderDeadline(LocalDate.now().minusDays(1))
                .build());

        when(lunchScheduleDAO.findOverlapLunchSchedule(scheduleDTO.getStartDate(), scheduleDTO.getEndDate()))
                .thenReturn(duplicatedSchedules);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            lunchScheduleService.createLunchSchedule(scheduleDTO);
        });

        assertEquals(OVERLAP_LUNCH_SCHEDULE, exception.getMessage());
    }

    @Test
    void createLunchSchedule_StartDateEqualEndDate_Successfully() throws BadRequestException {
        CreateLunchScheduleDTO lunchScheduleDTO = CreateLunchScheduleDTO.builder()
                .startDate(LocalDate.of(2024, 12, 20))
                .endDate(LocalDate.of(2024, 12, 20))
                .orderDeadline(LocalDate.of(2024, 12, 19))
                .build();
        LunchSchedule lunchSchedule = LunchSchedule.builder()
                .startDate(LocalDate.of(2024, 12, 20))
                .endDate(LocalDate.of(2024, 12, 20))
                .orderDeadline(LocalDate.of(2024, 12, 19))
                .build();
        LunchScheduleResponseDTO responseDTO = LunchScheduleResponseDTO.builder()
                .startDate(LocalDate.of(2024, 12, 20))
                .endDate(LocalDate.of(2024, 12, 20))
                .orderDeadline(LocalDate.of(2024, 12, 19))
                .build();

        when(lunchScheduleMapper.toScheduleEntity(lunchScheduleDTO)).thenReturn(lunchSchedule);
        when(lunchScheduleDAO.insert(lunchSchedule)).thenReturn(lunchSchedule);
        when(lunchScheduleMapper.toLunchScheduleDTO(lunchSchedule)).thenReturn(responseDTO);

        LunchScheduleResponseDTO actualResponse = lunchScheduleService.createLunchSchedule(lunchScheduleDTO);

        assertNotNull(actualResponse);

        verify(lunchScheduleMapper).toScheduleEntity(lunchScheduleDTO);
        verify(lunchScheduleDAO).insert(lunchSchedule);
        verify(lunchScheduleMapper).toLunchScheduleDTO(lunchSchedule);

    }

    @Test
    void createLunchSchedule_Successfully_Successfully() throws BadRequestException {
        CreateLunchScheduleDTO lunchScheduleDTO = CreateLunchScheduleDTO.builder()
                .startDate(LocalDate.of(2024, 12, 20))
                .endDate(LocalDate.of(2024, 12, 30))
                .orderDeadline(LocalDate.of(2024, 12, 19))
                .build();
        LunchSchedule lunchSchedule = LunchSchedule.builder()
                .startDate(LocalDate.of(2024, 12, 20))
                .endDate(LocalDate.of(2024, 12, 30))
                .orderDeadline(LocalDate.of(2024, 12, 19))
                .build();
        LunchScheduleResponseDTO responseDTO = LunchScheduleResponseDTO.builder()
                .startDate(LocalDate.of(2024, 12, 20))
                .endDate(LocalDate.of(2024, 12, 30))
                .orderDeadline(LocalDate.of(2024, 12, 19))
                .build();

        when(lunchScheduleMapper.toScheduleEntity(lunchScheduleDTO)).thenReturn(lunchSchedule);
        when(lunchScheduleDAO.insert(lunchSchedule)).thenReturn(lunchSchedule);
        when(lunchScheduleMapper.toLunchScheduleDTO(lunchSchedule)).thenReturn(responseDTO);

        LunchScheduleResponseDTO actualResponse = lunchScheduleService.createLunchSchedule(lunchScheduleDTO);

        assertNotNull(actualResponse);

        verify(lunchScheduleMapper).toScheduleEntity(lunchScheduleDTO);
        verify(lunchScheduleDAO).insert(lunchSchedule);
        verify(lunchScheduleMapper).toLunchScheduleDTO(lunchSchedule);
    }

}
