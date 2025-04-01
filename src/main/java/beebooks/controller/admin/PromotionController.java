package beebooks.controller.admin;

import beebooks.controller.BaseController;
import beebooks.ultilities.searchUtil.Search;
import beebooks.entities.Promotion;
import beebooks.entities.User;
import beebooks.service.PromotionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PromotionController extends BaseController {

    private final PromotionService promotionService;

    public PromotionController(final PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @RequestMapping(value = {"/admin/promotion/list"}, method = RequestMethod.GET)
    public String addPromotion(final Model model,
                               @ModelAttribute("searchModel") final Search searchModel) {

        model.addAttribute("promotionPage", promotionService.search(searchModel));
        model.addAttribute("searchModel", searchModel);

        return "admin/promotion";
    }

    @RequestMapping(value = {"/admin/promotion/add"}, method = RequestMethod.GET)
    public String getPromotion(final Model model) {

        Promotion promotion = new Promotion();
        model.addAttribute("promotion", promotion);

        return "admin/add-promotion";
    }

    @RequestMapping(value = {"/admin/promotion/adds"}, method = RequestMethod.POST)
    public String viewAddPromotion(@ModelAttribute("userLogined") User userLogined,
                                   final @ModelAttribute("promotion") Promotion promotion) {
        promotionService.save(promotion);

        return "redirect:/admin/promotion/list";
    }


    @RequestMapping(value = {"/admin/promotion/update/{promotionId}"}, method = RequestMethod.GET)
    public String editPromotion(final Model model,
                                @PathVariable("promotionId") int promotionId) {
        Promotion promotion = promotionService.findById(promotionId).orElse(null);
        model.addAttribute("promotion", promotion);

        return "admin/add-promotion";
    }
}
