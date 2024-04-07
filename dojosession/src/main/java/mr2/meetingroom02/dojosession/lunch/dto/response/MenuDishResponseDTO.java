package mr2.meetingroom02.dojosession.lunch.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class MenuDishResponseDTO {

    private LocalDate menuDate;

    private DishResponseDto dishResponseDto;

}
