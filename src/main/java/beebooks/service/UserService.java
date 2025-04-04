package beebooks.service;

import beebooks.entities.Role;
import beebooks.specifications.UserSpecification;
import beebooks.entities.User;
import beebooks.repository.UserRepository;
import beebooks.ultilities.searchUtil.UserSearch;
import beebooks.ultilities.ResultUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;


@Service
public class UserService extends BaseService<User,Integer> {

	private final UserRepository userRepository;
	private final RoleService roleService;

	public UserService(UserRepository userRepository, RoleService roleService) {
		super(userRepository);
		this.userRepository = userRepository;
        this.roleService = roleService;
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

	// Nếu cho phép update lên Role Admin nen tao them 1 role Master
	public ResultUtil updateRole(UserSearch userSearchModel) {
		Optional<User> userOptional = userRepository.findById(userSearchModel.getId());
		User user;
		if (userOptional.isPresent()) {
			user = userOptional.get();
			Optional<Role> role = roleService.findById(userSearchModel.getRoleId());
			if(role.isPresent()) {
				user.addRoles(role.get());
				userRepository.save(user);
				return new ResultUtil("succesMessage", "Update Thành công");
			}else{
				return new ResultUtil("errorMessage", "Không tồn tại Role");
			}
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

	@Transactional
    public ResultUtil registerUser(User user) {

		// đoạn này có theer hơi thừa vì front xử lý rồi
		if (user.getUsername() == null){
			return new ResultUtil("error","Tài khoản không được để trống!");
		} else if (user.getEmail() == null) {
			return new ResultUtil("error","Email không được để trống!");
		} else if (user.getPhone() == null) {
			return new ResultUtil("error","Số điện thoại không được để trống!");
		} else if (user.getPassword() == null) {
			return new ResultUtil("error","Mật khẩu không được để trống!");
		} else if (user.getAddress() == null) {
			return new ResultUtil("error","Địa chỉ không được để trống!");
		}

		Optional<User> usersMail = checkEmailRegister(user);
		Optional<User> usersName = checkUserNameRegister(user);

		if(usersMail.isPresent()){
			return new ResultUtil("error","Email của bạn đã được đăng ký !");
		}

		if(usersName.isPresent()){
			return new ResultUtil("error","Tài khoản của bạn đã được đăng ký !");
		}

		try {
			user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(4)));
			Role role = roleService.loadRoleByRoleName("USER");
			user.addRoles(role);
			save(user);
			return new ResultUtil("success","Cảm ơn bạn đã đăng ký thành công!!");
		}catch (Exception e){
			return new ResultUtil("error","Xảy ra lỗi khi đăng ký tài khoản.");
		}

    }
}
