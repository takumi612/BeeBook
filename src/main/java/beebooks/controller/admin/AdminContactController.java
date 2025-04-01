package beebooks.controller.admin;

import beebooks.controller.BaseController;
import beebooks.entities.Contact;
import beebooks.ultilities.searchUtil.Search;
import beebooks.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequiredArgsConstructor
public class AdminContactController extends BaseController {

    private final ContactService contactService;

    @RequestMapping(value = {"/admin/contact"}, method = RequestMethod.GET)
    public String getContact(final Model model,
                             @ModelAttribute ("searchModel") Search searchModel
    ){

        Page<Contact> contactPage = contactService.search(searchModel);
        model.addAttribute("contactPage", contactPage);
        model.addAttribute("searchModel", searchModel);

        return "admin/contact";
    }

    @GetMapping("/admin/deleteContact/{id}")
    public String deleteContact(@PathVariable("id") Integer id) {
        contactService.deleteById(id);
        return "redirect:/admin/contact";
    }
}
