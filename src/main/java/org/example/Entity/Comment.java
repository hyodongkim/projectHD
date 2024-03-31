package org.example.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@DynamicInsert
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Slf4j
@Table(name="comments")
@SequenceGenerator( name= "comment_num_gen",
        sequenceName = "comment_seq",
        initialValue = 1,
        allocationSize = 1)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_num_gen")
    @Column(name = "comment_id")
    private Long commentId;
    @Column(name = "writer")
    private String writer;
    @Column(name = "content")
    private String content;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date day;

    private Long articleId;


}