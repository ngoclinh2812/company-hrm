package mr2.meetingroom02.dojosession.lunch.mapper;

import mr2.meetingroom02.dojosession.lunch.dto.response.MenuResponseDTO;
import mr2.meetingroom02.dojosession.lunch.entity.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface MenuMapper {

    public MenuResponseDTO toResponseDTO(Menu menu);

}
