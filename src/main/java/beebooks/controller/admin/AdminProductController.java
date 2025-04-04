package beebooks.controller.admin;


import beebooks.controller.BaseController;
import beebooks.dto.ProductDetailDto;
import beebooks.entities.Product;
import beebooks.ultilities.ResultUtil;
import beebooks.ultilities.searchUtil.ProductSearch;
import beebooks.service.CategoriesService;
import beebooks.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AdminProductController extends BaseController {

    private final ProductService productService;
    private final CategoriesService categoriesService;

    @RequestMapping(value = {"/admin/product/list", "/admin/product"}, method = RequestMethod.GET)
    public String getProductList(final Model model,
                                 @ModelAttribute("searchModel") ProductSearch searchModel
    ) {
        Page<ProductDetailDto> products = productService.search(searchModel);

        model.addAttribute("productPage", products);
        model.addAttribute("searchModel", searchModel);
        model.addAttribute("categories", categoriesService.searchALl());

        return "admin/product";
    }

    @RequestMapping(value = {"/admin/product/list", "/admin/product"}, method = RequestMethod.POST)
    public String searchProductList(final Model model,
                                 @ModelAttribute("searchModel") ProductSearch searchModel
    ) {

        Page<ProductDetailDto> products = productService.search(searchModel);

        model.addAttribute("productPage", products);
        model.addAttribute("searchModel", searchModel);
        model.addAttribute("categories", categoriesService.searchALl());

        return "admin/product";
    }

    @RequestMapping(value = {"/admin/addProduct"}, method = RequestMethod.GET)
    public String viewAddProduct(final Model model) {
        model.addAttribute("product", new ProductDetailDto());
        productService.addProductFormAttributes(model);
        return "admin/add-product";
    }

    @RequestMapping(value = {"/admin/updateProduct/{productId}"}, method = RequestMethod.GET)
    public String editProduct(final Model model,
                              @PathVariable("productId") int productId) {

        Optional<Product> product=productService.findById(productId);

        if(product.isPresent()) {
            ProductDetailDto productDetailDto = new ProductDetailDto(product.get());
            model.addAttribute("product", productDetailDto);
            productService.addProductFormAttributes(model);
            return "admin/add-product";
        }
        else{
            model.addAttribute("errorMessage" , "Không tìm thấy sản phẩm");
            return "redirect:/admin/product";
        }

    }

    @RequestMapping(value = {"/admin/addProduct"}, method = RequestMethod.POST)
    public String saveProduct(Model model,
                              final @ModelAttribute("product") ProductDetailDto productDetailDto,
                              RedirectAttributes redirectAttributes
                             ){
        try {
            ResultUtil resultUtil = productService.addProduct(productDetailDto);

            if(resultUtil.getResult().equalsIgnoreCase("errorMessage")){
                productService.addProductFormAttributes(model);
                model.addAttribute( resultUtil.getResult(), resultUtil.getMessage());
                return "admin/add-product";
            }else if(resultUtil.getResult().equalsIgnoreCase("successMessage")){
                redirectAttributes.addFlashAttribute(resultUtil.getResult(), resultUtil.getMessage());
                return "redirect:product";
            }
        }catch (IOException e){
            model.addAttribute("errorMessage" , "Không thành công");
            productService.addProductFormAttributes(model);
        }
        return  "redirect:add-product";
    }

    @GetMapping("/admin/deleteProduct/{id}")
    @Transactional
    public String deleteProduct(@PathVariable("id") Integer id,
                                RedirectAttributes redirectAttributes) {
        log.info("--------Delete product with id: {}", id);
        if(productService.deleteProduct(id)){
            redirectAttributes.addFlashAttribute("successMessage","Xóa sản phẩm thành công");
        }else {
            redirectAttributes.addFlashAttribute("errorMessage","Không tìm thấy sản phẩm trong CSDL");
        }

        return "redirect:/admin/product";
    }


}
