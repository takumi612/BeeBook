package beebooks.controller.customer;

import beebooks.controller.BaseController;
import beebooks.dto.ProductDetailDto;
import beebooks.dto.ProductProjection;
import beebooks.ultilities.searchUtil.ProductSearch;
import beebooks.entities.Subscribe;
import beebooks.entities.User;
import beebooks.service.MailService;
import beebooks.service.ProductService;
import beebooks.service.SubscribeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController extends BaseController {

	private final ProductService productService;
	private final SubscribeService subscribeService;
	private final MailService mailService;


	@RequestMapping(value = { "/", "/home" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String home(final Model model,
					   @ModelAttribute("searchModel")ProductSearch searchModel) throws IOException {

		Page<ProductProjection> productList = productService.findAllToDto(searchModel.getPage());

		model.addAttribute("productsPage", productList);
		model.addAttribute("searchModel", searchModel);
		return "customer/index";
	}

	@RequestMapping(value = { "/product/{productId}"}, method = RequestMethod.GET)
	public String productDetails(final Model model,
								 @PathVariable("productId") final int productId,
								 @ModelAttribute("searchModel")ProductSearch searchModel
	){
		searchModel.setId(productId);
		Page<ProductDetailDto> products = productService.search(searchModel);
		ProductDetailDto product = products.getContent().get(0);

		model.addAttribute("product", product);
		return "customer/detail";
	}

	@RequestMapping(value = {"/subscribe"}, method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> subscribe(final Model model,
														 @ModelAttribute("userLogined") User userLogined,
														 final @RequestBody Subscribe subscribe) {
		Map<String, Object> jsonResult = new HashMap<>();
		model.addAttribute("subscribe", "");

		Subscribe subscribes = subscribeService.checkEmailSubscribe(subscribe);
		if(subscribes == null) {
			subscribeService.save(subscribe);
			jsonResult.put("message", "Cảm ơn, " + subscribe.getEmail() + " đã đăng kí thành công!");
            log.info("json result : {}", jsonResult);
			//gửi email thông báo
			String to = subscribe.getEmail();
			String subject = "XÁC NHẬN " + subscribe.getEmail() + " ĐÃ ĐĂNG KÝ THÀNH CÔNG!";
			String text = "Chúng tôi sẽ gửi cho bạn những thông tin mới nhất về Hiệu Sách"  + ".";
			mailService.sendEmailAsync(to, subject, text);
			return ResponseEntity.ok(jsonResult);
		} else {
			jsonResult.put("message", "Email này đã đăng ký");
		}
		return ResponseEntity.ok(jsonResult);
	}
	

}
