package org.example.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@DynamicInsert
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Slf4j
@ToString
@Table(name="board")
@SequenceGenerator( name= "board_id_seq_gen",
        sequenceName = "board_seq",
        initialValue = 1,
        allocationSize = 1)
public class Board {
    @Id
    @Column(name="board_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_id_seq_gen")
    private Long boardId;
    @Column(name="category")
    private Long category;
    @Column(name="title")
    private String title;
    @Column(name="description")
    private String description;

//    @ManyToOne(fetch= LAZY)
//    @JoinColumn(name = "article_id")
//    private Article article;
//
//    @OneToMany(mappedBy = "board",
//            cascade = CascadeType.ALL)
//    private List<Member> members = new ArrayList<>();
//


}
