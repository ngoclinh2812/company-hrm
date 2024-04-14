package mr2.meetingroom02.dojosession.lunch.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MenuServiceTest {

    @Test
    void createMenu_LunchScheduleIdNotFound_ThrowBadRequest(){}

    @Test
    void createMenu_NullLunchScheduleId_ThrowBadRequest(){}

    @Test
    void createMenu_NullMenuDate_ThrowBadRequest() {}

    @Test
    void createMenu_EmptyDishIds_Successfully() {}

    @Test
    void createMenu_DuplicatedMealWithinThisMonth_ThrowBadRequest() {}

    @Test
    void createMenu_DishIdNotFound_ThrowBadRequest() {}

    @Test
    void createMenu_MenuDateOutsideLunchSchedule_ThrowBadRequest() {}

    @Test
    void createMenu_MenuDateIsInTheWeekend_ThrowBadRequest() {}

    @Test
    void createMenu_MenuDateAlreadyExists_ThrowBadRequest() {}

}
