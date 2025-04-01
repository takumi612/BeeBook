package beebooks.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

@Controller
public class AdminHomeController {

    @RequestMapping(value = {"/admin", "/admin/home"}, method = RequestMethod.GET)
    public String home() throws IOException {
        return "admin/home";
    }

}
