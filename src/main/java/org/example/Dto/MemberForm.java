package org.example.Dto;

import lombok.*;

import javax.validation.constraints.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MemberForm {

    @NotBlank(message = "회원 아이디는 필수입니다.")
    @Pattern(regexp="[a-zA-Z1-9]{6,12}", message = "아이디는 영어와 숫자로 포함해서 6 ~ 12자리 사이로 입력해주세요.")
    private String id;

    @NotBlank(message = "회원 비밀번호는 필수입니다.")
    @Size(min=4, max=6, message = "비밀번호는 4~6자리 사이로 입력해 주세요.")
    private String password;

    @NotBlank(message = "회원 이름은 필수입니다.")
    private String name;

    @NotNull(message = "회원 나이는 필수입니다.")
    @Max(value = 100, message = "나이는 100을 초과할 수 없습니다.")
    private Integer age;

    @Email(message = "이메일 형식을 맞춰주세요.")
    private String email;

    private String introduction;
    private Date regdate;
}