package beebooks.entities;

import beebooks.dto.BlogDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name="blog")
public class Blog extends BaseEntity{
	
	@Column(name = "title", length = 100, nullable = false)
	private String title;
	
	@Column(name = "short_description", length = 3000, nullable = false)
	private String shortDes;

	@Column(name = "detail_description", columnDefinition = "LONGTEXT", nullable = false)
	private String details;
	
	@Column(name = "avatar")
	private String avatar;
	
	@Column(name = "seo", length = 1000)
	private String seo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_blog_id")
	private CategoriesBlog categoriesBlog;


	@OneToMany(mappedBy = "blog",fetch = FetchType.LAZY)
	private Set<BlogImage> blogImages = new HashSet<>();


	public void addBlogImage(BlogImage _blogImage) {
		_blogImage.setBlog(this);
		blogImages.add(_blogImage);
	}
	public Blog(){}

	public Blog(BlogDto blogDto){
		this.title = blogDto.getTitle();
		this.shortDes = blogDto.getShortDes();
		this.details = blogDto.getDetails();
		this.avatar = blogDto.getAvatar();
		this.seo = blogDto.getSeo();
	}


}
