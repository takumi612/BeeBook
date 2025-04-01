package beebooks.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "category_blog")
public class CategoriesBlog extends BaseEntity {

	@Column(name = "name", length = 100, nullable = false)
	private String name;

	@Column(name = "description", length = 100, nullable = false)
	private String description;

	@Column(name = "seo", length = 1000)
	private String seo;

	@OneToMany(mappedBy = "categoriesBlog",fetch = FetchType.LAZY)
	private Set<Blog> blogs = new HashSet<>();

}
