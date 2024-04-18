package mr2.meetingroom02.dojosession.base.exception.message;

import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
public class LunchScheduleExceptionMessage {

    public static final String LUNCH_SCHEDULE_NOT_FOUND = "Lunch schedule not found";

    public static final String START_DATE_IS_BEFORE_CURRENT_DATE = "Start date of the schedule must be after the current date";

    public static final String ORDER_FOR_THIS_SCHEDULE_IS_CLOSED = "Orders for this lunch schedule is already closed";
    public static final String START_DATE_IS_AFTER_END_DATE = "Start date of schedule must be before end date";

    public static final String ORDER_DEADLINE_MUST_BE_BEFORE_START_DATE = "Order deadline must be before start date";

    public static final String OVERLAP_LUNCH_SCHEDULE = "This lunch schedule is overlap with other ones";

    public static final String ONE_MENU_CAN_NOT_CONTAINS_OVER_20_DISHES = "One menu can not contains over 20 dishes";

    public static String menuDateOutOfLunchSchedulePeriod(LocalDate startDate, LocalDate endDate) {
        return String.format("Menu date must be inside lunch schedule, between " + startDate.toString() +
                " and " + endDate.toString());
    }

    public static String mealNotFoundInMenu(Long mealId) {
        return String.format("Meal " + mealId.toString() +
                " is not found in the menu");
    }

    public static String lunchIsNotServedInWeekend(LocalDate menuDate) {
        return String.format("Your menu date on " + menuDate +
                " is in the weekend. Lunch is not served on weekends.");
    }

    public static String menuAlreadyExisted(LocalDate date) {
        return String.format("Menu on day " + date.toString() + " already exists.");
    }

    public static String mealAlreadySelectedWithinThisMonth(String dishName) {
        return String.format("Dish " + dishName + " has already been selected within this month");
    }
}
