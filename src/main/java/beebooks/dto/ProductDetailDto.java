package beebooks.dto;

import beebooks.entities.Product;
import beebooks.entities.ProductImage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.ZoneId;
import java.util.Date;
import java.time.LocalDateTime;
import java.util.Set;


@Getter
@Setter
@RequiredArgsConstructor
public class ProductDetailDto {
    private Integer id;
    private String title;
    private double price;
    private String publicationYear;//năm xuất bản

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date releaseDate;//ngày phát hành
    private Integer quantity;//Số lượng sản phẩm
    private String shortDes;
    private String details;
    private String avatar;
    private String seo;
    private boolean status;

    private String categoriesName;
    private int categoriesId;
    private String manufacturerName;
    private int manufacturerId;
    private String authorName;
    private int authorId;
    private String promotionName;
    private int promotionId;

    private LocalDateTime createdDate;
    private String createdBy;

    private Set<ProductImage> productImage;

    MultipartFile productAvatar;
    MultipartFile[] productPictures;

    public ProductDetailDto(Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.price = product.getPrice();
        this.publicationYear = product.getPublicationYear();
        this.releaseDate = Date.from(product.getReleaseDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
        this.quantity = product.getQuantity();
        this.shortDes = product.getShortDes();
        this.details = product.getDetails();
        this.avatar = product.getAvatar();
        this.seo = product.getSeo();
        this.status = product.getStatus();
        this.productImage = product.getProductImage();
        this.manufacturerName = product.getManufacturer().getName();
        this.manufacturerId = product.getManufacturer().getId();
        this.categoriesName = product.getCategories() != null ? product.getCategories().getName() : "";
        this.categoriesId = product.getCategories() != null ? product.getCategories().getId() : 0;
        this.authorName = product.getAuthor() != null ? product.getAuthor().getName() : "";
        this.authorId = product.getAuthor() != null ? product.getAuthor().getId() : 0;
        this.promotionName = product.getPromotion() != null ? product.getPromotion().getName() : "";
        this.promotionId = product.getPromotion() != null ? product.getPromotion().getId() : 0;
        this.createdDate = product.getCreatedDate();
        this.createdBy = product.getCreatedBy();
    }

}
