package org.example.Service;

import org.example.Dto.AttachmentType;
import org.example.Entity.Attachment;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface AttachmentService {

    public List<Attachment> saveAttachments(Map<AttachmentType, List<MultipartFile>> multipartFileListMap) throws IOException;

    public Map<AttachmentType, List<Attachment>> findAttachments();
}

