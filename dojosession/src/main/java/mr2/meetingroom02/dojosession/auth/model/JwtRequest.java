package mr2.meetingroom02.dojosession.auth.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Wrong email format")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;
}