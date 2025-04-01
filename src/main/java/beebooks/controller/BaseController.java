package beebooks.controller;

import beebooks.entities.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ModelAttribute;

public abstract class BaseController {

    @ModelAttribute("isLogined")
    public boolean isLogin() {
        boolean isLogined = false;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            isLogined = true;
        }
        return isLogined;
    }

    @ModelAttribute("userLogined")
    public User getUserLogin() {
        Object userLogined = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userLogined instanceof UserDetails)
            return (User) userLogined;
        return null;
    }

}
