package org.example.Entity;

import jakarta.persistence.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.File;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.FetchType.LAZY;

@Entity // 테이블 정의
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name="num_article_store_gen", sequenceName="article_store_seq", allocationSize=1)
@Table(name = "article_store")
@DynamicUpdate
public class Article_Store {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="num_article_store_gen")
    @Column(name="article_num")
    private Long articleNum;

    @Column(name="originFilename")
    private String originFilename;
    @Column(name="storeFilename")
    private String storeFilename;

    @ManyToOne(fetch= LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    public void getOriginFilename(String fname1) {
        originFilename = fname1;
    }

    public void getStoreFilename(String absolutePath) {
        storeFilename = absolutePath;
    }





}