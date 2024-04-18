package mr2.meetingroom02.dojosession.menu.dto;

import lombok.Builder;
import lombok.Data;
import mr2.meetingroom02.dojosession.dish.dto.DishResponseDTO;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class MenuResponseDTO {

    private Long id;

    private LocalDate menuDate;

    private List<DishResponseDTO> dishResponseDTOS;

}
