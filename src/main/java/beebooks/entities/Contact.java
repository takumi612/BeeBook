package beebooks.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@Table(name = "contact")
public class Contact extends BaseEntity {

	@Column(name = "name", length = 100, nullable = false)
	private String name;

	@Column(name = "email", length = 45, nullable = false)
	private String email;

	@Column(name = "massage", length = 1000, nullable = false)
	private String massage;

}
