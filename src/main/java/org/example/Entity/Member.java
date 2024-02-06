package org.example.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.example.Dto.Gender;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

@Entity
@DynamicInsert
@ToString
@Getter
@Setter
@SequenceGenerator( name= "member_id_seq_gen",
        sequenceName = "member_seq",
        allocationSize = 1)
@Table(name = "member")
@EntityListeners(AuditingEntityListener.class)
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
    @CreatedDate
    private LocalDateTime day;
    @Column(name="member_introduction")
    private String introduction;
    @Column(name="member_photo")
    private String photo;

    public Member(){}
}
