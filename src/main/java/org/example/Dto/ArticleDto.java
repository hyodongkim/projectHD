package org.example.Dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@DynamicInsert
@RequiredArgsConstructor
@AllArgsConstructor
@Slf4j
@Getter
@Setter
@ToString
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class ArticleDto {


    private Long articleId;

    @NotBlank(message = "닉네임 입력이 되지 않았습니다.")
    private String name;
    @NotBlank(message = "주제 입력이 되지 않았습니다.")
    private String subject;
    @NotBlank(message = "내용 입력이 되지 않았습니다.")
    private String content;
    @CreatedDate
    private LocalDateTime day;

    private Integer hitcount;

    private Integer clickcount;

    private String viewSetCountArticle;
    private String viewSetHitArticle;

    private String viewSetArticle;
    @NotNull(message = "입력이 되지 않았습니다.")
    private Long member;



}
