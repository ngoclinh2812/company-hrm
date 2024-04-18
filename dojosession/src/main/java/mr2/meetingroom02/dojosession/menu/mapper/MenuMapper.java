package mr2.meetingroom02.dojosession.menu.mapper;

import mr2.meetingroom02.dojosession.menu.dto.MenuResponseDTO;
import mr2.meetingroom02.dojosession.menu.entity.Menu;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface MenuMapper {

    public MenuResponseDTO toResponseDTO(Menu menu);

}
