package org.example.Entity;

import lombok.*;
import org.example.Dto.Gender;
import org.hibernate.annotations.DynamicInsert;
import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@DynamicInsert
@RequiredArgsConstructor
@ToString
@Getter
@Setter
public class Member{
    @Id @GeneratedValue
    @Column(name="member_id")
    private long id;
    @Column(name="member_userid",unique = true)
    private String userid;
    @Column(name="member_password")
    private String password;
    @Column(name="member_email")
    private String email;
    @Column(name="member_name")
    private String name;
    @Column(name="member_sex")
    private Gender sex;
    @Column(name="member_age")
    private Integer age;
    @Column(name="member_birth")
    private String birth;
    @Column(name="member_regdate")
    private LocalDateTime regdate;
    @Column(name="member_introduction")
    private String introduction;
    @Column(name="member_photo")
    private String photo;

    public Member(long id, String userid, String password, String email, String name, Gender sex, Integer age, String birth, LocalDateTime regdate, String introduction, String photo) {
        this.id = id;
        this.userid = userid;
        this.password = password;
        this.email = email;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.birth = birth;
        this.regdate = regdate;
        this.introduction = introduction;
        this.photo = photo;
    }

}
