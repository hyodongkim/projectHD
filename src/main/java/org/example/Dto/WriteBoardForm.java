package org.example.Dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
public class WriteBoardForm {
    private Long articleId;

    private String name;

    private String subject;

    private String content;
    @CreatedDate
    private LocalDateTime day;
}
