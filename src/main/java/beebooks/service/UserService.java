package beebooks.service;

import beebooks.entities.Role;
import beebooks.repository.RoleRepository;
import beebooks.specifications.UserSpecification;
import beebooks.entities.User;
import beebooks.repository.UserRepository;
import beebooks.ultilities.searchUtil.UserSearch;
import beebooks.ultilities.ResultUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;


@Service
public class UserService extends BaseService<User,Integer> {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;

	public UserService(UserRepository userRepository, RoleRepository roleRepository) {
		super(userRepository);
		this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

	public Optional<User> loadUserByUsername(String userName) {
        return userRepository.findByUsername(userName);
	}


	public Page<User> search(UserSearch searchModel) {
		Specification<User> spec = UserSpecification.searchByModel(searchModel);
		return userRepository.findAll(spec,searchModel.getPage());
	}


	public ResultUtil deleteUserById(Integer id) {
		Optional<User> userOptional = userRepository.findById(id);

		if (userOptional.isPresent()) {
			User user = userOptional.get();
			Set<Role> roles = user.getRoles();
			boolean hasUserRole = roles.stream().anyMatch(role -> role.getName().equals("USER"));
			boolean hasAdminRole = roles.stream().anyMatch(role -> role.getName().equals("ADMIN"));

			if (hasUserRole && !hasAdminRole) { // chỉ xóa nếu có vai trò 'user' và không có vai trò 'admin'
				super.deleteById(id);
				return new ResultUtil("successMessage", "Deactivate User thành công");
			}
			else{
				return new ResultUtil("errorMessage", "Không thể xóa Admin");
			}
		}else{
			return new ResultUtil("errorMessage", "Không tồn tại User");
		}
	}

	public ResultUtil updateRole(UserSearch userSearchModel) {
		Optional<User> userOptional = userRepository.findById(userSearchModel.getId());
		User user;
		if (userOptional.isPresent()) {
			user = userOptional.get();
			Role role = roleRepository.findById(userSearchModel.getRoleId()).orElse(null);
			user.addRoles(role);
			userRepository.save(user);
			return new ResultUtil("succesMessage", "Update Thành công");
		}else{
			return new ResultUtil("errorMessage", "Không tồn tại User");
		}
	}

	@Transactional
	public Optional<User> checkUserNameRegister(User user){
		return userRepository.findByUsername(user.getUsername());
	}

	@Transactional
	public Optional<User> checkEmailRegister(User user) {
		return userRepository.findByEmail(user.getEmail());
	}

}
