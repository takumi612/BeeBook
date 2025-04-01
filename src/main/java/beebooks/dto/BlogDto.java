package beebooks.dto;

import beebooks.entities.BlogImage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
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

    private Set<BlogImage> blogImages;

    private MultipartFile productAvatar;

    private MultipartFile[] productPictures;
}
