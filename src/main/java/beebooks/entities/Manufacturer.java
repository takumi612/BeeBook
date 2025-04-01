package beebooks.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "manufacturer")
public class Manufacturer extends BaseEntity {

	@Column(name = "name", length = 100, nullable = false)
	private String name;

	@Column(name = "address", length = 1000, nullable = false)
	private String address;

	@OneToMany(mappedBy = "manufacturer",fetch = FetchType.LAZY)
	private Set<Product> products = new HashSet<>();

}
