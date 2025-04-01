package beebooks.controller.admin;


import beebooks.controller.BaseController;
import beebooks.dto.PaymentStatusDto;
import beebooks.dto.SaleOrderDto;
import beebooks.ultilities.searchUtil.OrderSearch;
import beebooks.entities.*;
import beebooks.service.SaleOrderProductsService;
import beebooks.service.SaleOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AdminOrderController extends BaseController{
	
	private final SaleOrderService saleorderService;
	private final SaleOrderProductsService saleOrderProductsService;

	@RequestMapping(value = { "/admin/order" }, method = RequestMethod.GET)
	public String getOrder(final Model model,
						   @ModelAttribute("searchModel")OrderSearch searchModel
	) {
		Page<SaleOrderDto> saleOrders = saleorderService.search(searchModel);

		model.addAttribute("orderPage", saleOrders);
		model.addAttribute("searchModel", searchModel);

		return "admin/order";
	}

	@RequestMapping(value = { "/admin/order" }, method = RequestMethod.POST)
	public String searchOrder(final Model model,
						   @ModelAttribute("searchModel")OrderSearch searchModel
	) {
		Page<SaleOrderDto> saleOrders = saleorderService.search(searchModel);
		model.addAttribute("orderPage", saleOrders);
		model.addAttribute("searchModel", searchModel);
		return "admin/order";
	}

	@RequestMapping(value = { "/admin/orderProduct/{orderCode}" }, method = RequestMethod.GET)
	public String getOrderProduct(final Model model,
								  @PathVariable("orderCode") String orderCode,
								  @ModelAttribute("searchModel")OrderSearch searchModel
	){
		searchModel.setCode(orderCode);
		SaleOrderDto saleOrderDto = saleorderService.findByCode(orderCode);
		Page<SaleorderProducts> saleOrderProducts = saleOrderProductsService.getByOrderCode(searchModel);

		model.addAttribute("orderInfo", saleOrderDto);
		model.addAttribute("orderProductsPage", saleOrderProducts);
		model.addAttribute("searchModel", searchModel);

		return "admin/order-product";
	}

	@RequestMapping(value = {"/admin/orderReject"}, method = RequestMethod.POST)
	public String rejectOrder(@ModelAttribute("userLogined") User user,
							  @RequestParam("orderCode") String orderCode,
							  @RequestParam("reason") String reason,
							  @RequestParam("returnUrl") String returnUrl,
							  RedirectAttributes redirectAttributes,
							  HttpServletRequest request) {

		SaleOrder saleorder =new SaleOrder();
		saleorder.setCode(orderCode);
		saleorder.setReason(reason);

		String redirectPath;
		String contextPath = request.getContextPath();
		String defaultRedirectUrl = contextPath + "/admin/order"; // URL mặc định nếu có lỗi

		try {
			saleorderService.rejectOrder(saleorder);
			redirectAttributes.addFlashAttribute("success", "Đã hủy đơn hàng '" + orderCode + "' thành công!");
			// Xác thực và sử dụng returnUrl
			if (isValidRedirectUrl(returnUrl, contextPath)) {
				redirectPath = returnUrl;
			} else {
				log.warn("Invalid or potentially unsafe returnUrl provided: '{}'. Falling back to default.", returnUrl);
				redirectPath = defaultRedirectUrl;
			}
		}catch  (Exception e) {
			log.error("Error rejecting order {}: {}", orderCode, e.getMessage(), e);
			redirectAttributes.addFlashAttribute("error", "Lỗi khi hủy đơn hàng: " + e.getMessage());
			// Ngay cả khi lỗi, vẫn cố gắng quay lại trang trước đó hoặc trang mặc định
			if (isValidRedirectUrl(returnUrl, contextPath)) {
				redirectPath = returnUrl;
			} else {
				redirectPath = defaultRedirectUrl;
			}
		}

		return "redirect:" + redirectPath;
	}

	@GetMapping("/deleteOrderProduct/{id}")
	public String deleteOrderProduct(@PathVariable("id") Integer id) {
		saleorderService.deleteOrderProduct(id);
		return "redirect:/admin/order-product";
	}

	@PostMapping( "/admin/orderProduct/updateStatus")
	public String updatePaymentStatus(@RequestBody PaymentStatusDto paymentStatusDto){
		saleorderService.updatePaymentStatus(paymentStatusDto);
		return "redirect:/admin/order";
	}

	private boolean isValidRedirectUrl(String url, String contextPath) {
		if (url == null || url.trim().isEmpty()) {
			return false;
		}
		try {
			URI uri = new URI(url);
			// Chỉ cho phép URL tương đối bắt đầu từ context path và nằm trong khu vực admin
			// Ngăn chặn chuyển hướng đến domain khác hoặc các khu vực không mong muốn
			return !uri.isAbsolute() && url.startsWith(contextPath + "/admin/");
		} catch (URISyntaxException e) {
			// Nếu URL không hợp lệ cú pháp
			return false;
		}
	}
}


