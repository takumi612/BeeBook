package beebooks.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name="images")
public class Images extends BaseEntity{
	@Column(name = "title", length = 500, nullable = false)
	private String title;
	
	@Column(name = "path", length = 200, nullable = false)
	private String path;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

}
