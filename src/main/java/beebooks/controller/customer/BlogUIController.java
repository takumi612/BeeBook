package beebooks.controller.customer;

import beebooks.controller.BaseController;
import beebooks.ultilities.searchUtil.BlogSearch;
import beebooks.entities.Blog;
import beebooks.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class BlogUIController extends BaseController {

	private final BlogService blogService;


	public BlogUIController(BlogService blogService) {
		this.blogService = blogService;
	}

	@RequestMapping(value = { "/blog" }, method = RequestMethod.GET)
	public String getAllBlog(final Model model,
							@ModelAttribute("searchModel") BlogSearch searchModel) {

		model.addAttribute("blogPages", blogService.search(searchModel));
		model.addAttribute("searchModel", searchModel);
		return "customer/blog";
	}

	@RequestMapping(value = { "/blog/details/{blogSeo}"}, method = RequestMethod.GET)
	public String getBlogDetail(final Model model,
								@PathVariable("blogSeo") String blogSeo) {

		List<Blog> blogs = blogService.findBySeo(blogSeo);
		Blog blog = blogs.get(0);

		model.addAttribute("blog", blog);
		return "customer/details-blog";
	}

}
