package org.example.Dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LoginForm {

    @NotBlank(message = "아이디를 입력하여 주세요.")
    @Pattern(regexp="[a-zA-Z1-9]{8,20}", message = "아이디는 영어와 숫자로 포함해서 8 ~ 20자리 사이로 입력해주세요.")
    private String userid;
    @NotBlank(message = "비밀번호를 입력하여 주세요.")
    @Size(min=4, max=12, message = "비밀번호는 10~20자리 사이로 입력해 주세요.")
    private String password;
    private Boolean remember;
    private String redirect;
}
