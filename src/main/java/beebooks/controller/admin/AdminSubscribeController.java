package beebooks.controller.admin;

import beebooks.controller.BaseController;
import beebooks.entities.Subscribe;
import beebooks.ultilities.searchUtil.Search;
import beebooks.service.SubscribeService;
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
public class AdminSubscribeController extends BaseController {

    private final SubscribeService subscribeService;

    @RequestMapping(value = {"/admin/subscribe"}, method = RequestMethod.GET)
    public String getSubscribe(final Model model,
                               @ModelAttribute("searchModel") Search searchModel
    ) {
        Page<Subscribe> subscribePage = subscribeService.search(searchModel);

        model.addAttribute("subscribePage", subscribePage);
        model.addAttribute("searchModel", searchModel);

        return "admin/subscribe";
    }

    @GetMapping("/admin/deleteSubscribe/{id}")
    public String deleteSubscribe(@PathVariable("id") Integer id) {
        subscribeService.deleteById(id);
        return "redirect:/admin/subscribe";
    }
}
