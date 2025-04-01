package beebooks.controller.admin;

import beebooks.controller.BaseController;
import beebooks.ultilities.searchUtil.Search;
import beebooks.entities.Author;
import beebooks.entities.User;
import beebooks.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
public class AuthorController extends BaseController {

    private final AuthorService authorService;

    @RequestMapping(value = {"/admin/author/list"}, method = RequestMethod.GET)
    public String getAuthor(final Model model,
                            @ModelAttribute("searchModel") Search searchModel
    ) {
        Page<Author> authorPage = authorService.search(searchModel);
        model.addAttribute("authorPage", authorPage);
        model.addAttribute("searchModel", searchModel);
        return "admin/author";
    }


    @RequestMapping(value = {"/admin/author/add"}, method = RequestMethod.GET)
    public String viewAddAuthor(final Model model) {
        Author author = new Author();
        model.addAttribute("author", author);
        return "admin/add-author";
    }

    @RequestMapping(value = {"/admin/author/adds"}, method = RequestMethod.POST)
    public String saveAuthor(final @ModelAttribute("author") Author author,
                             @ModelAttribute("userLogined") User userLogined) {
        authorService.save(author);
        return "redirect:/admin/author/list";
    }

    @RequestMapping(value = {"/admin/author/update/{authorId}"}, method = RequestMethod.GET)
    public String viewEditAuthor(final Model model,
                                 @PathVariable("authorId") int authorId) {
        Author author = authorService.findById(authorId).orElse(null);
        model.addAttribute("author", author);
        return "admin/add-author";
    }
}
