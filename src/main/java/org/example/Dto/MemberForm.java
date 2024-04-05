package org.example.Dto;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
public class MemberForm {

    private Long id;

    @NotBlank(message = "회원 아이디는 필수입니다.")
    @Pattern(regexp="[a-zA-Z0-9]{8,20}", message = "아이디는 영어와 숫자로 포함해서 8 ~ 20자리 사이로 입력해주세요.")
    private String userid;

    @NotBlank(message = "회원 비밀번호는 필수입니다.")
    @Size(min=10, max=20, message = "비밀번호는 10~20자리 사이로 입력해 주세요.")
    private String password;

    @NotBlank(message = "회원 이메일은 필수입니다.")
    @Email(message = "이메일 형식을 맞춰주세요.")
    private String email;

    @NotBlank(message = "회원 이름은 필수입니다.")
    private String name;

    @Enumerated(value = EnumType.STRING)
    private Gender sex;

    @NotNull(message = "회원 나이는 필수입니다.")
    @Max(value = 100, message = "나이는 100을 초과할 수 없습니다.")
    private Integer age;

    @NotBlank(message = "회원 생년월일은 필수입니다.")
    private String birth;

    @CreatedDate
    private LocalDateTime day;

    @NotBlank(message = "회원 자기소개는 필수입니다.")
    private String introduction;

    @Enumerated(value = EnumType.STRING)
    private Position job;
}
