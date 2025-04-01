package beebooks.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "roles")
public class Role extends BaseEntity implements GrantedAuthority{
	@Column(name = "name", length = 100, nullable = false)
	private String name;

	@Column(name = "description", length = 45, nullable = false)
	private String description;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "role_id")
			, inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> users = new HashSet<>();

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return name;
	}

}
