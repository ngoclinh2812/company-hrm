package mr2.meetingroom02.dojosession.lunchSchedule.service;

import mr2.meetingroom02.dojosession.base.exception.BadRequestException;
import mr2.meetingroom02.dojosession.base.exception.NotFoundException;
import mr2.meetingroom02.dojosession.lunchOrder.dao.LunchOrderDAO;
import mr2.meetingroom02.dojosession.lunchSchedule.dao.LunchScheduleDAO;
import mr2.meetingroom02.dojosession.menu.dao.MenuDAO;
import mr2.meetingroom02.dojosession.lunchSchedule.dto.*;
import mr2.meetingroom02.dojosession.lunchSchedule.entity.LunchSchedule;
import mr2.meetingroom02.dojosession.menu.entity.Menu;
import mr2.meetingroom02.dojosession.lunchSchedule.mapper.LunchScheduleMapper;
import mr2.meetingroom02.dojosession.utils.excel.ExcelExporter;
import org.hibernate.MappingException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static mr2.meetingroom02.dojosession.base.exception.message.LunchScheduleExceptionMessage.*;

@Stateless
public class LunchScheduleService {

    @Inject
    private LunchScheduleDAO lunchScheduleDAO;

    @Inject
    private MenuDAO menuDAO;

    @Inject
    private LunchScheduleMapper lunchScheduleMapper;

    @Inject
    private LunchOrderDAO lunchOrderDAO;

    @Inject
    private ExcelExporter excelExporter;

    @Transactional
    public LunchScheduleResponseDTO createLunchSchedule(CreateLunchScheduleDTO scheduleDTO)
            throws InternalError, MappingException, BadRequestException {
        checkValidDateSchedule(scheduleDTO.getStartDate(), scheduleDTO.getEndDate(), scheduleDTO.getOrderDeadline());
        LunchSchedule lunchSchedule = lunchScheduleMapper.toScheduleEntity(scheduleDTO);
        LunchSchedule savedLunchSchedule = lunchScheduleDAO.insert(lunchSchedule);
        return lunchScheduleMapper.toLunchScheduleDTO(savedLunchSchedule);
    }

    public LunchScheduleResponseDTO getLunchScheduleById(Long scheduleId) throws NotFoundException {
        LunchSchedule lunchSchedule = lunchScheduleDAO.getScheduleLunch(scheduleId);
        if (lunchSchedule == null) {
            throw new NotFoundException(LUNCH_SCHEDULE_NOT_FOUND);
        } else {
            List<Menu> menus = menuDAO.getAllByLunchScheduleId(lunchSchedule.getId());
            return lunchScheduleMapper.toLunchScheduleDTO(lunchSchedule);
        }
    }

    private void checkValidDateSchedule(LocalDate startDate, LocalDate endDate, LocalDate orderDeadline) throws BadRequestException {
        if (startDate.isBefore(LocalDate.now())) throw new BadRequestException(START_DATE_IS_BEFORE_CURRENT_DATE);
        if (startDate.isAfter(endDate)) throw new BadRequestException(START_DATE_IS_AFTER_END_DATE);
        if(orderDeadline.isAfter(startDate) || orderDeadline.isEqual(startDate)) throw new BadRequestException(ORDER_DEADLINE_MUST_BE_BEFORE_START_DATE);
        checkOverlapSchedule(startDate, endDate);
    }

    private void checkOverlapSchedule(LocalDate startDate, LocalDate endDate) throws BadRequestException {
        List<LunchSchedule> lunchSchedules = lunchScheduleDAO.findOverlapLunchSchedule(startDate, endDate);
        if (!lunchSchedules.isEmpty()) {
            throw new BadRequestException(OVERLAP_LUNCH_SCHEDULE);
        }
    }

    public LunchScheduleResponseDTO getLunchScheduleUpcomingWeek() {
        LunchSchedule lunchSchedules = lunchScheduleDAO.getLunchScheduleUpcomingWeek();
        return lunchScheduleMapper.toLunchScheduleDTO(lunchSchedules);
    }
}

