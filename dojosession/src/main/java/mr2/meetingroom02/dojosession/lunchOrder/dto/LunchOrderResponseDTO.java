package mr2.meetingroom02.dojosession.lunchOrder.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import mr2.meetingroom02.dojosession.menuDish.dto.MenuDishResponseDTO;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public class LunchOrderResponseDTO {
    private Long employeeId;

    List<MenuDishResponseDTO> menuDishes;

}
