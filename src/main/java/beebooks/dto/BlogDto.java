package beebooks.dto;

import beebooks.entities.Images;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class BlogDto {
    private Integer id;

    private String title;

    private String shortDes;

    private String details;

    private String avatar;

    private String seo;

    private int categoryId;

    private LocalDateTime createdDate;

    private String createdBy;

    private Set<Images> images;

    private MultipartFile productAvatar;
}
