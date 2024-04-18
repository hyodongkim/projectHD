package org.example.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
//@ToString
@Getter
@Setter
@SequenceGenerator( name= "see_catalog_seq_gen",
        sequenceName = "see_catalog_seq",
        allocationSize = 1)
@DynamicInsert
@DynamicUpdate
@Table(name = "see_catalog")
@EntityListeners(AuditingEntityListener.class)
public class SeeCatalog {
    @Id
    @Column(name="see_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "see_catalog_seq_gen")
    private Long seeId;

    @Column(name="see_info_member_id")
    private Long seeInfoMemberId;

    @Column(name="see_info_article_id")
    private Long seeInfoArticleId;

    @Column(name="see_info_click")
    private Long seeInfoClick;

    @Column(name="see_info_hit")
    private Long seeInfoHit;
}
