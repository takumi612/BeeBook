package beebooks.controller;

import beebooks.entities.User;
import beebooks.service.RoleService;
import beebooks.service.UserService;
import beebooks.ultilities.ResultUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequiredArgsConstructor
public class RegisterController extends BaseController{

    private final UserService userService;

    @RequestMapping(value = { "/register" }, method = RequestMethod.GET)
    public String getRegister(final Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @RequestMapping(value = {"/register"}, method = RequestMethod.POST)
    public String registerUser(final @ModelAttribute("user") User user,
                               Model model,
                               RedirectAttributes redirectAttributes) throws IllegalStateException{

        ResultUtil resultUtil = userService.registerUser(user);

        if(resultUtil.getResult().equalsIgnoreCase("error")){
            model.addAttribute("error",resultUtil.getMessage());
            return "register";
        }else if(resultUtil.getResult().equalsIgnoreCase("success")){
            redirectAttributes.addFlashAttribute("success",resultUtil.getMessage());
            return "redirect:/login";
        }
        return "register";
    }
}
