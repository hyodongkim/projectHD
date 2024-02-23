package org.example.Entity;

import org.example.Entity.Member;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity // 테이블 정의
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Store {
    @Id
    @SequenceGenerator(name="seq_gen", sequenceName="seq_shopping", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq_shopping")
    private Long num;

    @ManyToOne // 여러 상품을 한 판매자가 등록할 수 있음
    @JoinColumn(name="member_id", nullable=true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member; // 판매자

}