package org.example.Dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class HdMemberForm {

    @NotBlank(message = "회원 아이디는 필수입니다.")
    @Pattern(regexp = "[a-zA-Z1-9]{6,12}", message = "아이디는 영어와 숫자로 포함해서 6 ~ 12자리 사이로 입력해주세요.")
    private String id;

    @NotBlank(message = "회원 비밀번호는 필수입니다.")
    @Size(min = 4, max = 12, message = "비밀번호는 4~12자리 사이로 입력해 주세요.")
    private String password;

    @NotBlank(message = "회원 이름은 필수입니다.")
    private String name;

    @NotNull(message = "회원 나이는 필수입니다.")
    @Max(value = 100, message = "나이는 100을 초과할 수 없습니다.")
    private Integer age;

    private LocalDateTime regdate;
}
