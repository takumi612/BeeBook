package beebooks.entities;

import beebooks.dto.ProductDetailDto;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name="products")
public class Product extends BaseEntity{

    @Column(name = "title", length = 100, nullable = false)
	private String title;
	
	@Column(name = "price", precision = 13, scale = 2, nullable = false)
	private double price;

	@Column(name = "publication_year")
	private String publicationYear;//năm xuất bản

	@Column(name = "release_date")
	private LocalDate releaseDate;//ngày phát hành

	@Column(name = "quantity")
	private Integer quantity;//Số lượng sản phẩm
	
	@Column(name = "short_description", length = 3000, nullable = false)
	private String shortDes;

	@Column(name = "detail_description", columnDefinition = "LONGTEXT", nullable = false)
	private String details;
	
	@Column(name = "avatar")
	private String avatar;
	
	@Column(name = "seo", length = 1000)
	private String seo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private Categories categories;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "manufacturer_id")
	private Manufacturer manufacturer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "author_id")
	private Author author;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "promotion_id")
	private Promotion promotion;

	@OneToMany(mappedBy = "product",fetch = FetchType.LAZY)
	private Set<ProductImage> productImage = new HashSet<>();


	public void addProductImage(ProductImage _productImage) {
		_productImage.setProduct(this);
		productImage.add(_productImage);
	}

	public  boolean isPromotionValid() {
		if(this.getPromotion()==null){
			return false;
		}else {
			LocalDateTime startDate = this.getPromotion().getStartDate();
			LocalDateTime endDate = this.getPromotion().getEndDate();
			LocalDateTime currentDate = LocalDateTime.now();

			 return !currentDate.isBefore(startDate) && !currentDate.isAfter(endDate);
		}
	}

	public Product(){}

	public Product(ProductDetailDto productDetailDto) {
		this.setId(productDetailDto.getId());
		this.title = productDetailDto.getTitle();
		this.price = productDetailDto.getPrice();
		this.publicationYear = productDetailDto.getPublicationYear();
		this.releaseDate = productDetailDto.getReleaseDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		this.quantity = productDetailDto.getQuantity();
		this.shortDes = productDetailDto.getShortDes();
		this.details = productDetailDto.getDetails();
		this.avatar = productDetailDto.getAvatar();
		this.seo = productDetailDto.getSeo();
		this.setCreatedBy(productDetailDto.getCreatedBy());
		this.setCreatedDate(productDetailDto.getCreatedDate());
	}
}
