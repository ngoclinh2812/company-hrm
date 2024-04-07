package mr2.meetingroom02.dojosession.lunch.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class MenuResponseDTO {

    private Long id;

    private LocalDate menuDate;

    private List<DishResponseDto> dishResponseDtos;

}
