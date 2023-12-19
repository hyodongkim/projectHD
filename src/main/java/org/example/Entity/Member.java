package org.example.Entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@DynamicInsert
@RequiredArgsConstructor
@ToString
@Getter
public class Member{
    @Id
    @Column(name="member_id")
    private String id;
    @Column(name="password")
    private String password;
    @Column(name="name")
    private String name;
    @Column(name="age")
    private Integer age;
    @Column(name="regdate")
    private LocalDateTime regdate;

    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setRegdate(LocalDateTime regdate) {
        this.regdate = regdate;
    }

    public Member(String id, String password, String name, Integer age, LocalDateTime regdate) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.age = age;
        this.regdate = regdate;
    }
}
