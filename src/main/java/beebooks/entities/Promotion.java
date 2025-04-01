package beebooks.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "promotion")
public class Promotion extends BaseEntity {

	@Column(name = "name", length = 1000, nullable = false)
	private String name;

	@Column(name = "percent")
	private Double percent;

	@OneToMany(mappedBy = "promotion",fetch = FetchType.LAZY)
	private Set<Product> products = new HashSet<>();

	@Column(name = "start_date")
	private LocalDateTime startDate;

	@Column(name = "end_date")
    private LocalDateTime endDate;

}
