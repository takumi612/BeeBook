package beebooks.controller.customer;

import beebooks.controller.BaseController;
import beebooks.dto.CustomerDto;
import beebooks.ultilities.Cart;
import beebooks.ultilities.CartItem;
import beebooks.entities.SaleOrder;
import beebooks.entities.User;
import beebooks.service.ProductService;
import beebooks.service.SaleOrderService;
import beebooks.ultilities.ResultUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;
import java.util.Objects;


@Controller
@Slf4j
@RequiredArgsConstructor
public class CartController extends BaseController {

    private final ProductService productService;
    private final SaleOrderService saleOrderService;

    @ModelAttribute("customerDto")
    public CustomerDto getUserDto(@ModelAttribute("userLogined") User userLogined) {
        CustomerDto customerDto = new CustomerDto();
        if (isLogin()) {
            customerDto.setCustomerName(userLogined.getUsername());
            customerDto.setCustomerEmail(userLogined.getEmail());
            customerDto.setCustomerPhone(userLogined.getPhone());
            customerDto.setCustomerAddress(userLogined.getAddress());
        }
        return customerDto;
    }

    @RequestMapping(value = {"/cart/checkout"}, method = RequestMethod.POST)
    public String checkOutCart(final HttpServletRequest request,
                               @ModelAttribute("userLogined") User userLogined,
                               @Valid @ModelAttribute("customerDto") CustomerDto customerDto,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {

        // Validate user input
        if (bindingResult.hasErrors()) {
            return "customer/cart";
        }
        SaleOrder saleOrder = new SaleOrder(customerDto);
        // Lấy Cart đã được lưu trong Session
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            ResultUtil resultUtil = saleOrderService.checkOutCart(userLogined,cart,saleOrder);

            if(Objects.equals(resultUtil.getResult(), "error")){

                redirectAttributes.addFlashAttribute(resultUtil.getResult(), resultUtil.getMessage());
                return "redirect:/cart/view";

            }else if(Objects.equals(resultUtil.getResult(), "success")){
                // Clear the cart after successful checkout
                cart = new Cart();
                session.setAttribute("cart", cart);
                session.setAttribute("totalItems", "0");
                redirectAttributes.addFlashAttribute(resultUtil.getResult(), resultUtil.getMessage());
            }
        }
        return "redirect:/home";
    }

    @RequestMapping(value = {"/cart/view"}, method = RequestMethod.GET)
    public String viewCart() {
        return "customer/cart";
    }

    @GetMapping("cart/remove/{productId}")
    public String removeProduct(final HttpServletRequest request,
                                @PathVariable("productId") int productId) {
        productService.removeProductFromCart(request,productId);
        return "redirect:/cart/view";
    }

    @RequestMapping(value = {"/updateQuantityCart/{action}"}, method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> updateQuantityCart(final HttpServletRequest request,
                                                                  final @RequestBody CartItem cartItem,
                                                                  @PathVariable("action") String action) {
        return productService.updateQuantityCart(request,cartItem,action);
    }

    @RequestMapping(value = {"/addToCart"}, method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> addToCart(final HttpServletRequest request,
                                                         final @RequestBody CartItem cartItem) {
        return productService.addItemToCart(request,cartItem);
    }


}