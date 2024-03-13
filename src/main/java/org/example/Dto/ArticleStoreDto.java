package org.example.Dto;

import org.example.Entity.Article;
import org.example.Entity.Member;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ArticleStoreDto {
    private Long articleNum;
    private Article aricle;
    private MultipartFile file;
}

