package beebooks.controller.admin;

import beebooks.controller.BaseController;
import beebooks.ultilities.searchUtil.UserSearch;
import beebooks.entities.User;
import beebooks.service.UserService;
import beebooks.ultilities.ResultUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AdminUserController extends BaseController {

	private final UserService userService;

	@RequestMapping(value = { "/admin/user/list","/admin/user" }, method = RequestMethod.GET)
	public String getUser(final Model model,
						  @ModelAttribute("searchModel") UserSearch searchModel
	) {
		Page<User> users = userService.search(searchModel);
		model.addAttribute("userPage", users);
		model.addAttribute("searchModel", searchModel);
		return "admin/user";
	}

	@GetMapping("/admin/deleteUser/{id}")
	public String deleteUser(@PathVariable("id") Integer id,
							 RedirectAttributes redirectAttributes) {
		ResultUtil response = userService.deleteUserById(id);
		redirectAttributes.addFlashAttribute(response.getResult(), response.getMessage());
		return "redirect:/admin/user";
	}

	@PostMapping( "/admin/user/updateRole")
	public String updateUserRole(@RequestBody UserSearch userSearchModel){
		userService.updateRole(userSearchModel);
		return "redirect:/admin/user";
	}
}
