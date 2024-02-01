package org.example.Dto;

import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Date;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class MemberForm {

    private Long id;

    @NotBlank(message = "회원 아이디는 필수입니다.")
    @Pattern(regexp="[a-zA-Z1-9]{8,20}", message = "아이디는 영어와 숫자로 포함해서 8 ~ 20자리 사이로 입력해주세요.")
    private String userid;

    @NotBlank(message = "회원 비밀번호는 필수입니다.")
    @Size(min=10, max=20, message = "비밀번호는 10~20자리 사이로 입력해 주세요.")
    private String password;

    @Email(message = "이메일 형식을 맞춰주세요.")
    private String email;

    @NotBlank(message = "회원 이름은 필수입니다.")
    private String name;

    @Enumerated(value = EnumType.STRING)
    private Gender sex;

    @NotNull(message = "회원 나이는 필수입니다.")
    @Max(value = 100, message = "나이는 100을 초과할 수 없습니다.")
    private Integer age;

    private String birth;

    private LocalDateTime regdate;

    private String introduction;

    private String photo;

}
