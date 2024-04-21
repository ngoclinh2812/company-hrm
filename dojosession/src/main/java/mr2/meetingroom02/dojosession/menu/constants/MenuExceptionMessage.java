package mr2.meetingroom02.dojosession.menu.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuExceptionMessage {
    public static final String ONE_MENU_CAN_NOT_CONTAINS_OVER_20_DISHES = "One menu can not contains over 20 dishes";

    public static final String MAIN_PROTEIN_TYPE_DUPLICATED_WITHIN_TWO_DAYS = "Main protein types should not be duplicated within 2 days in the lunch schedules";

    public static String menuDateOutOfLunchSchedulePeriod(LocalDate startDate, LocalDate endDate) {
        return String.format("Menu date must be inside lunch schedule, between %s and %s", startDate, endDate);
    }

    public static String lunchIsNotServedInWeekend(LocalDate menuDate) {
        return String.format("Your menu date on %s is in the weekend. Lunch is not served on weekends.", menuDate);
    }

    public static String menuAlreadyExisted(LocalDate date) {
        return String.format("Menu on day %s already exists", date.toString());
    }

}
