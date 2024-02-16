package org.example.Entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import jakarta.persistence.*;

@Entity
@Table(name="aa")
@DynamicInsert
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MyEntity {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}