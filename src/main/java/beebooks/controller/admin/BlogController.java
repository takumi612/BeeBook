package beebooks.controller.admin;


import beebooks.controller.BaseController;
import beebooks.dto.BlogDto;
import beebooks.service.CategoriesBlogService;
import beebooks.ultilities.searchUtil.BlogSearch;
import beebooks.entities.Blog;
import beebooks.entities.User;
import beebooks.service.BlogService;
import beebooks.ultilities.ResultUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequiredArgsConstructor
public class BlogController extends BaseController {

    private final BlogService blogService;
    private final CategoriesBlogService categoriesBlogService;

    @RequestMapping(value = {"/admin/blog/list", "/admin/blog"}, method = RequestMethod.GET)
    public String getBlog(final Model model,
                          @ModelAttribute("searchModel") BlogSearch searchModel
    ) {

        model.addAttribute("blogPage", blogService.search(searchModel));
        model.addAttribute("searchModel", searchModel);
        model.addAttribute("categoriesBlog",categoriesBlogService.findAll());

        return "admin/blog";
    }

    @RequestMapping(value = {"/admin/addBlog"}, method = RequestMethod.GET)
    public String viewAddBlog(final Model model) {
        model.addAttribute("BlogDto", new BlogDto());
        model.addAttribute("categoriesBlog",categoriesBlogService.findAll());
        return "admin/add-blog";
    }

    @RequestMapping(value = {"/admin/addBlog/{blogId}"}, method = RequestMethod.GET)
    public String viewEditBlog(final Model model,
                               @PathVariable("blogId") int blogId) {

        Blog blog = blogService.findById(blogId).orElse(null);
        model.addAttribute("BLog", blog);
        return "admin/add-blog";
    }

    @GetMapping("/admin/deleteBlog/{id}")
    public String deleteBlog(@PathVariable("id") Integer id,
                             RedirectAttributes redirectAttributes) {
        ResultUtil response =blogService.deleteBlog(id);
        redirectAttributes.addFlashAttribute(response.getResult(), response.getMessage());
        return "redirect:/admin/blog";
    }


    @RequestMapping(value = {"/admin/blog/addBlog"}, method = RequestMethod.POST)
    public String addBlog(@ModelAttribute("BlogDto") BlogDto blogDto) throws Exception {
        blogService.save(blogDto);
        return "redirect:/admin/blog";
    }

}
