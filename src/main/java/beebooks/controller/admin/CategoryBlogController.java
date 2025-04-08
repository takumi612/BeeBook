package beebooks.controller.admin;


import beebooks.controller.BaseController;
import beebooks.ultilities.searchUtil.BlogSearch;
import beebooks.entities.CategoriesBlog;
import beebooks.entities.User;
import beebooks.service.CategoriesBlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class CategoryBlogController extends BaseController {

    private final CategoriesBlogService categoriesBlogService;

    @RequestMapping(value = {"/admin/categoryBlog/list", "/admin/categoryBlog"}, method = RequestMethod.GET)
    public String getCategoryBlog(final Model model,
                                  @ModelAttribute("searchModel") BlogSearch searchModel
    ) {
        model.addAttribute("categoriesBlogPage", categoriesBlogService.search(searchModel));
        model.addAttribute("searchModel", searchModel);
        model.addAttribute("categoriesBlog",categoriesBlogService.findAll());

        return "admin/category-blog";
    }

    @RequestMapping(value = {"/admin/addCategoryBlog"}, method = RequestMethod.GET)
    public String viewAddCategoryBlog(final Model model) {

        CategoriesBlog categoriesBlog = new CategoriesBlog();
        model.addAttribute("categoriesBlog", categoriesBlog);
        return "admin/add-category-blog";
    }


    @RequestMapping(value = {"/admin/addCategoryBlog"}, method = RequestMethod.POST)
    public String addCategoryBlog(final @ModelAttribute("categoriesBlog") CategoriesBlog categoriesBlog) {
        categoriesBlogService.save(categoriesBlog);
        return "redirect:/admin/categoryBlog";
    }


    @RequestMapping(value = {"/admin/editCategoryBlog/{categoriesId}"}, method = RequestMethod.GET)
    public String editCategoryBlog(final Model model, @PathVariable("categoriesId") int categoriesId) {
        CategoriesBlog categoriesBlog = categoriesBlogService.findById(categoriesId).orElse(null);
        model.addAttribute("categoriesBlog", categoriesBlog);

        return "admin/add-category-blog";
    }
}
