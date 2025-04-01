package beebooks.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "subscribe")
public class Subscribe extends BaseEntity {

	@Column(name = "email", length = 45, nullable = false)
	private String email;

}
