package org.example.Dto;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@Getter
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
    private LocalDateTime regdate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public LocalDateTime getRegdate() {
        return regdate;
    }

    public void setRegdate(LocalDateTime regdate) {
        this.regdate = regdate;
    }

    public MemberForm(String id, String password, String name, Integer age, String email, String introduction, LocalDateTime regdate) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.age = age;
        this.email = email;
        this.introduction = introduction;
        this.regdate = regdate;
    }
}
