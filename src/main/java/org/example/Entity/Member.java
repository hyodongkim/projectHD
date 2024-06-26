package org.example.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.Dto.Gender;
import org.example.Dto.Position;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
//@ToString
@Getter
@Setter
@SequenceGenerator( name= "member_id_seq_gen",
        sequenceName = "member_seq",
        allocationSize = 1)
@DynamicInsert
@DynamicUpdate
@Table(name = "member")
@EntityListeners(AuditingEntityListener.class)
public class Member{
    @Id
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_id_seq_gen")
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
    @Column(name="member_job")
    private Position job;
    @Column(name="member_viewSetCount")
    private String viewSetCount;
    @Column(name="member_viewSetHit")
    private String viewSetHit;

    @OneToMany(mappedBy = "member")
    private List<Store> stores = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Article> articles = new ArrayList<>();

    public Member(){}
}
