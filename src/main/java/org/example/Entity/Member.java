package org.example.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.example.Dto.Gender;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@DynamicInsert
@RequiredArgsConstructor
@ToString
@Getter
@Setter
@SequenceGenerator( name= "member_id_seq_gen",
        sequenceName = "member_seq",
        initialValue = 1,
        allocationSize = 1)
public class Member{
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_id_seq_gen")
    @Column(name="member_id")
    private Long id;
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
    @Column(name="member_first")
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime day;
    @Column(name="member_introduction")
    private String introduction;
    @Column(name="member_photo")
    private String photo;

    public Member(Long id, String userid, String password, String email, String name, Gender sex, Integer age, String birth, LocalDateTime day, String introduction, String photo) {
        this.id = id;
        this.userid = userid;
        this.password = password;
        this.email = email;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.birth = birth;
        this.day = day;
        this.introduction = introduction;
        this.photo = photo;
    }
}
