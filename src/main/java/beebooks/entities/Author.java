package beebooks.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "author")
public class Author extends BaseEntity {

	@Column(name = "name", length = 100, nullable = false)
	private String name;

	@Column(name = "biography", length = 10000, nullable = false)
	private String biography;

	@OneToMany(mappedBy = "author",fetch = FetchType.LAZY)
	private Set<Product> products = new HashSet<Product>();

}
