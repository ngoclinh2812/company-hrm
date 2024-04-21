package mr2.meetingroom02.dojosession.base.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Getter
@RequiredArgsConstructor
public class ExceptionBody implements Serializable {
    private final String errorKey;
    private final String errorMessage;
    private final Integer statusCode;

    public ExceptionBody(Integer status, String errorKey, String errorMessage) {
        this.errorKey = errorKey;
        this.errorMessage = errorMessage;
        this.statusCode = status;
    }
}
