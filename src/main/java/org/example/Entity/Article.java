package org.example.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@DynamicInsert
@RequiredArgsConstructor
@AllArgsConstructor
@Slf4j
@Getter
@Setter
@ToString
@Table(name="article")
@SequenceGenerator( name= "article_id_seq_gen",
        sequenceName = "article_seq",
        initialValue = 1,
        allocationSize = 1)
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "article_id_seq_gen")
    @Column(name = "article_id")
    private Long articleId;
    @Column(name = "name")
    private String name;
    @Column(name = "subject")
    private String subject;
    @Column(name = "content")
    private String content;
    @CreatedDate
    private LocalDateTime day;
//    @Column(name = "hitcount")
//    private Long hitcount;
//    @Column(name = "group_no")
//    private Long groupNo;
//    @Column(name = "level_no")
//    private Long levelNo;
//    @Column(name = "order_no")
//    private Long orderNo;

    private Long member;



}