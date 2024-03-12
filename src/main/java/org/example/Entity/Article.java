package org.example.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

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
//@DynamicUpdate
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "article_id_seq_gen")
    @Column(name = "article_id")
    private Long articleId;
    @Column(name = "writer")
    private String writer;
    @Column(name = "subject")
    private String subject;
    @Column(name = "content")
    private String content;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date regdate;
    @Column(name = "hitcount")
    private Long hitcount;
    @Column(name = "group_no")
    private Long groupNo;
    @Column(name = "level_no")
    private Long levelNo;
    @Column(name = "order_no")
    private Long orderNo;

    @ManyToOne(fetch= LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @OneToMany(mappedBy = "article",
            cascade = CascadeType.ALL)
    private List<Board> boards = new ArrayList<>();
}