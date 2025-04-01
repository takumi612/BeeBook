package beebooks.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name="blog_images")
public class BlogImage extends BaseEntity{
	@Column(name = "title", length = 500, nullable = false)
	private String title;
	
	@Column(name = "path", length = 200, nullable = false)
	private String path;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "blogId")
	private Blog blog;

}
