package org.example.Entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Embeddable
@AllArgsConstructor
public class UploadFile {
    private String uploadFilename;  // 작성자가 업로드한 파일명
    private String storeFilename;   // 서버 내부에서 관리하는 파일명
}