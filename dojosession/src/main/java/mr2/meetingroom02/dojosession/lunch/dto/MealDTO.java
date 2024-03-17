package mr2.meetingroom02.dojosession.lunch.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MealDTO implements Serializable {

    private String name;
}
