package beebooks.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails{
	@Column(name = "username", length = 100, nullable = false, unique = true)
	private String username;

	@Column(name = "password", length = 100, nullable = false)
	private String password;

	@Getter
	@Column(name = "email", length = 45, nullable = false, unique = true)
	private String email;
	
	@Getter
	@Column(name = "address", length = 1000, nullable = false)
	private String address;

	@Getter
	@Column(name = "phone", length = 10)
	private String phone;

	@Getter
	@ManyToMany(mappedBy = "users",fetch = FetchType.LAZY)
	private Set<Role> roles = new HashSet<>();

	public void addRoles(Role role) {
		role.getUsers().add(this);
		roles.add(role);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return roles;
	}

	public boolean isAdmin() {
		return this.roles.stream()
				.filter(Objects::nonNull)
				.map(Role::getName)
				.filter(Objects::nonNull)
				.anyMatch("ADMIN"::equalsIgnoreCase); // Không tìm thấy Role với tên "admin"
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getUsername() {
		return username;
	}

    @Override
	public String getPassword() {
		return password;
	}

}
