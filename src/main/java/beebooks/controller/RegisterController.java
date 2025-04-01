package beebooks.controller;

import beebooks.entities.Role;
import beebooks.entities.User;
import beebooks.service.RoleService;
import beebooks.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class RegisterController extends BaseController{

    private final UserService userService;
    private final RoleService roleService;

    @RequestMapping(value = { "/register" }, method = RequestMethod.GET)
    public String getRegister(final Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @RequestMapping(value = {"/register"}, method = RequestMethod.POST)
    public String registerUser(final @ModelAttribute("user") User user,
                               @ModelAttribute("userLogined") User userLogined,
                               Model model,
                               RedirectAttributes redirectAttributes) throws IllegalStateException{

        if (user.getUsername() == null){
            model.addAttribute("error","Tài khoản không được để trống!");
            return "register";
        } else if (user.getEmail() == null) {
            model.addAttribute("error","Email không được để trống!");
            return "register";
        } else if (user.getPhone() == null) {
            model.addAttribute("error","Số điện thoại không được để trống!");
            return "register";
        } else if (user.getPassword() == null) {
            model.addAttribute("error","Mật khẩu không được để trống!");
            return "register";
        } else if (user.getAddress() == null) {
            model.addAttribute("error","Địa chỉ không được để trống!");
            return "register";
        }

        Optional<User> usersMail = userService.checkEmailRegister(user);
        Optional<User> usersName = userService.checkUserNameRegister(user);

        if(usersMail.isPresent()){
            model.addAttribute("error","Email của bạn đã được đăng ký!");
            return "register";
        }

        if(usersName.isPresent()){
            model.addAttribute("error","Tài khoản của bạn đã được đăng ký!");
            return "register";
        }

        try {
                user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(4)));
                Role role = roleService.loadRoleByRoleName("USER");
                user.addRoles(role);
                userService.save(user);
                redirectAttributes.addFlashAttribute("success","Cảm ơn bạn đã đăng ký thành công!!");
                return "redirect:/login";
        }catch (Exception e){
            model.addAttribute("error", "Đã xảy ra lỗi khi đăng ký tài khoản");
            return "register";
        }
    }
}
