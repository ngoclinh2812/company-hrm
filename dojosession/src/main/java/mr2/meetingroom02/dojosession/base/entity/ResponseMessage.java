package mr2.meetingroom02.dojosession.base.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
@Data
public class ResponseMessage implements Serializable {
    private Boolean success;
    private int statusCode;
    private String message;
}
