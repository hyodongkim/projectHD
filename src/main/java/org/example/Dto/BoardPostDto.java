package org.example.Dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.Entity.Attachment;
import org.example.Entity.Board;
import org.example.Entity.Member;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@NoArgsConstructor
public class BoardPostDto {

    private Long id;
    private String userid;
    private String password;
    private String email;
    private String name;
    private Gender sex;
    private Integer age;
    private String birth;
    private LocalDateTime day;
    private String introduction;
    private List<Attachment> attachedFiles = new ArrayList<>();

    @Builder
    public BoardPostDto(Long id,String userid,String password, String email, String name, Gender sex, Integer age, String birth, LocalDateTime day, String introduction,
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

    public Board createBoard() {
        return Board.builder()
                .id(id)
                .userid(userid)
                .password(password)
                .email(email)
                .name(name)
                .sex(sex)
                .age(age)
                .birth(birth)
                .day(day)
                .introduction(introduction)
                .attachedFiles(new ArrayList<>())
                .build();
    }
}
