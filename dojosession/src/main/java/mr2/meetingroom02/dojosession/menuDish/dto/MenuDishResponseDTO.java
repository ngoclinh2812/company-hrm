package mr2.meetingroom02.dojosession.menuDish.dto;

import lombok.Builder;
import lombok.Data;
import mr2.meetingroom02.dojosession.dish.dto.DishResponseDTO;

import java.time.LocalDate;

@Data
@Builder
public class MenuDishResponseDTO {

    private LocalDate menuDate;

    private DishResponseDTO dishResponseDto;

}
