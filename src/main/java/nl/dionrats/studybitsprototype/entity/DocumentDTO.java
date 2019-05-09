package nl.dionrats.studybitsprototype.entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class DocumentDTO {
    private String name;
    private String type;
    private String key;
    private MultipartFile file;

    public DocumentDTO(String name, String type, String key) {
        this.name = name;
        this.type = type;
        this.key = key;
    }
}
