package beebooks.controller.admin;

import beebooks.controller.BaseController;
import beebooks.ultilities.searchUtil.ProductSearch;
import beebooks.entities.Categories;
import beebooks.entities.User;
import beebooks.service.CategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class CategoryProductController extends BaseController {

    private final CategoriesService categoriesService;

    @RequestMapping(value = {"/admin/categoryProduct"}, method = RequestMethod.GET)
    public String getCategoryProduct(final Model model,
                                     @ModelAttribute("searchModel")  ProductSearch searchModel) {

        model.addAttribute("categoriesWithPaging", categoriesService.searchALl());
        model.addAttribute("searchModel", searchModel);

        return "admin/category-product";
    }

    @RequestMapping(value = {"/admin/add-category"}, method = RequestMethod.GET)
    public String viewAddCategoryProduct(final Model model) {
        Categories categories = new Categories();
        model.addAttribute("categories", categories);
        return "admin/add-category";
    }


    @RequestMapping(value = {"/admin/add-category"}, method = RequestMethod.POST)
    public String saveCategoryProduct(Model model,@ModelAttribute("userLogined") User userLogined,
                                      final @ModelAttribute("categories") Categories categories
    ) {
        categoriesService.save(categories);
        model.addAttribute("successMessage","Thêm mới thành công");
        return "redirect:categoryProduct";
    }

    @RequestMapping(value = {"/admin/add-category/{categoriesId}"}, method = RequestMethod.GET)
    public String viewEditCategoryProduct(final Model model, @PathVariable("categoriesId") int categoriesId) {
        Categories categories = categoriesService.findById(categoriesId).orElse(null);
        model.addAttribute("categories", categories);

        return "admin/add-category";
    }
}
