package org.example.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.Dto.Gender;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Console;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@DynamicInsert
@ToString
@Getter
@Setter
@SequenceGenerator( name= "member_id_seq_gen",
        sequenceName = "member_seq",
        allocationSize = 1)
@Table(name = "board")
@EntityListeners(AuditingEntityListener.class)
public class Board{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_id_seq_gen")
    @Column(name="board_id")
    private Long id;
    @Column(name="board_userid",unique = true)
    private String userid;
    @Column(name="board_password")
    private String password;
    @Column(name="board_email")
    private String email;
    @Column(name="board_name")
    private String name;
    @Column(name="board_sex")
    private Gender sex;
    @Column(name="board_age")
    private Integer age;
    @Column(name="board_birth")
    private String birth;
    @Column(name="board_first")
    @CreatedDate
    private LocalDateTime day;
    @Column(name="board_introduction")
    private String introduction;


    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Attachment> attachedFiles = new ArrayList<>();

    public Board(){}

    @Builder
    public Board(Long id,String userid,String password, String email, String name, Gender sex, Integer age, String birth, LocalDateTime day, String introduction,
                 List<Attachment> attachedFiles) {
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
        this.attachedFiles = attachedFiles;

    }
}
