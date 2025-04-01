package beebooks.controller.customer;

import beebooks.controller.BaseController;
import beebooks.dto.ProductProjection;
import beebooks.ultilities.searchUtil.ProductSearch;
import beebooks.entities.Categories;
import beebooks.service.CategoriesService;
import beebooks.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CollectionController extends BaseController {

	private final ProductService productService;
	private final CategoriesService categoriesService;

	@ModelAttribute("categories")
	public List<Categories> getAllCategories(){
		return categoriesService.searchALl();
	}

	@RequestMapping(value = { "/collection" }, method = RequestMethod.GET)
	public String viewCollection(final Model model,
								 @ModelAttribute("searchModel") final ProductSearch searchModel) {

		model.addAttribute("productsPage", productService.findAllToDto(searchModel.getPage()));
		model.addAttribute("searchModel", searchModel);

		return "customer/collection";
	}

	@RequestMapping(value = { "/collection" }, method = RequestMethod.POST)
	public String findCollection(final Model model,
								 @ModelAttribute("searchModel") final ProductSearch searchModel) {

		Page<ProductProjection> productList;

		if(searchModel.keyword!=null && !searchModel.keyword.isEmpty()) {
			productList = productService.findProductByTitle(searchModel);
		}else if(searchModel.categoryId == 0) {
			productList = productService.findAllToDto(searchModel.getPage());
		}else {
			productList = productService.findByCategories(searchModel);
		}

		model.addAttribute("productsPage", productList);
		model.addAttribute("searchModel", searchModel);

		return "customer/collection";
	}


}
