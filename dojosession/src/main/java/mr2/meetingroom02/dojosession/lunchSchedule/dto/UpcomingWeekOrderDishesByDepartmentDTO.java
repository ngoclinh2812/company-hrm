package mr2.meetingroom02.dojosession.lunchSchedule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpcomingWeekOrderDishesByDepartmentDTO {

    private LocalDate menuDate;

    private String departmentName;

    private String dishName;

    private Long count;

}
