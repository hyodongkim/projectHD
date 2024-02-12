package org.example.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Entity
@DynamicInsert
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Slf4j
@ToString
@Table(name="images")
@SequenceGenerator( name= "image_id_seq_gen",
        sequenceName = "images_seq",
        initialValue = 1,
        allocationSize = 1)
public class Image {
    @Id
    @Column(name="image_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "image_id_seq_gen")
    private Long imageId;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    @Column(name="img_cont_type")
    private String imgContType;

    @Column(name="img_file_name")
    private String imgFileName;

    public Image(Member member, String imgContType, String imgFileName) {
        this.member = member;
        this.imgContType = imgContType;
        this.imgFileName = imgFileName;
    }
}