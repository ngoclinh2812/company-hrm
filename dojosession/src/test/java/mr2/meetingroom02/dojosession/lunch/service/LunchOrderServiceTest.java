package mr2.meetingroom02.dojosession.lunch.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LunchOrderServiceTest {

    @Test
    void createLunchOrder_OrderDateIsAfterOrderDeadline_ThrowBadRequest() {}

    @Test
    void createLunchOrder_menuDishIdNotFound_ThrowBadRequest() {}

}
