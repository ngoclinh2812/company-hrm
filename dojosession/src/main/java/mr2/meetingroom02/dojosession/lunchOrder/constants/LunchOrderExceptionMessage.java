package mr2.meetingroom02.dojosession.lunchOrder.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LunchOrderExceptionMessage {

    public static final String ORDER_FOR_THIS_SCHEDULE_IS_CLOSED = "This lunch schedule is already closed";

    public static String mealAlreadySelectedWithinThisMonth(String dishName) {
        return String.format("Dish %s has already been selected within this month", dishName);
    }

}
