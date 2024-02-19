package org.example.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.Dto.Gender;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import java.util.List;

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
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_id_seq_gen")
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

    @Embedded
    private UploadFile attachFile;          // 첨부 파일
    @Embedded
    private List<UploadFile> imageFiles;    // 첨부 이미지

    public Member(){}
}
