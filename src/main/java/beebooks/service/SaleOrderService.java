package beebooks.service;

import beebooks.dto.PaymentStatusDto;
import beebooks.dto.SaleOrderDto;
import beebooks.entities.Product;
import beebooks.entities.SaleorderProducts;
import beebooks.entities.User;
import beebooks.entities.enums.PaymentStatus;
import beebooks.specifications.SaleOrderSpecification;
import beebooks.entities.SaleOrder;
import beebooks.ultilities.Cart;
import beebooks.ultilities.CartItem;
import beebooks.ultilities.ResultUtil;
import beebooks.ultilities.searchUtil.OrderSearch;
import beebooks.repository.SaleOrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class SaleOrderService extends BaseService<SaleOrder,Integer> {

	private final SaleOrderRepository saleOrderRepository;
	private final ProductService productService;
	private final MailService mailService;
	private final SaleOrderProductsService saleorderProductsService;

    public SaleOrderService(SaleOrderRepository saleOrderRepository, ProductService productService, MailService mailService, SaleOrderProductsService saleorderProductsService) {
        super(saleOrderRepository);
		this.saleOrderRepository = saleOrderRepository;
        this.productService = productService;
        this.mailService = mailService;
        this.saleorderProductsService = saleorderProductsService;
    }


	public Page<SaleOrderDto> search(OrderSearch searchModel) {
		Specification<SaleOrder> spec = SaleOrderSpecification.getSaleOrderSpecification(searchModel);
		Page<SaleOrder> saleOrders= saleOrderRepository.findAll(spec, searchModel.getPage());
		return saleOrders.map(SaleOrderDto::new);
	}
	@Transactional
	public ResultUtil checkOutCart(User userLogined, Cart cart, SaleOrder saleOrder) {
		ResultUtil response;
		saleOrder.setCreatedBy(getUserName(userLogined));

		try {
			double totalPrice = 0.0;
			for (CartItem cartItem : cart.getCartItems()) {

				// Khởi tạo đối tượng saleOrderProducts
				SaleorderProducts saleOrderProducts = new SaleorderProducts();
				Optional<Product> product = productService.findById(cartItem.getProductId());
				if(product.isPresent()) {
					saleOrderProducts.setProduct(product.get());
					saleOrderProducts.setQuantity(cartItem.getQuantity());
					saleOrderProducts.setTotal(cartItem.getPriceUnit() * cartItem.getQuantity());
				}else{
					return new ResultUtil("error", "Đặt hàng thất bại, sản phẩm không có");
				}
				// Kiểm tra hàng tồn và cập nhật ố lượng trong kho
				if (cartItem.getQuantity() != 0) {
					// Giảm số lượng sản phẩm trong kho
					int remainingQuantity = product.get().getQuantity() - cartItem.getQuantity();
					if (remainingQuantity < 0) {
						return new ResultUtil("error", "Đặt hàng thất bại, sản phẩm " + product.get().getTitle() + " không đủ hàng ");
					} else {
						product.get().setQuantity(remainingQuantity);
						// Cập nhật sản phẩm trong cơ sở dữ liệu
						productService.save(product.get());
						totalPrice = totalPrice + cartItem.getPriceUnit() * cartItem.getQuantity();
						saleOrder.addSaleOrderProducts(saleOrderProducts);
					}
				}
			}

			saleOrder.setTotalPrice(totalPrice);

			saleOrder = save(userLogined, saleOrder);

			response = new ResultUtil("success", "Đặt hàng thành công");

			// Gửi email đơn hàng
			String to = saleOrder.getCustomerEmail();
			String subject = "XÁC NHẬN ĐƠN HÀNG #" + saleOrder.getCode();
			String text = "Cảm ơn bạn đã đặt hàng. Mã đơn hàng của bạn là #" + saleOrder.getCode() + ".";
			mailService.sendEmail(to, subject, text);
		} catch (Exception e) {
			return new ResultUtil("error", "Đã xảy ra lỗi: " + e.getMessage());
		}
		return response;
}

	public void deleteOrderProduct(int id){
		Optional<SaleOrder> saleorderOptional = saleOrderRepository.findById(id);
		if (saleorderOptional.isPresent()) {
			SaleOrder saleorder = saleorderOptional.get();

			Set<SaleorderProducts> saleOrderProducts = saleorder.getSaleOrderProducts();
			saleorderProductsService.deleteAll(saleOrderProducts);
			saleOrderRepository.delete(saleorder);
		}
	}

	public void updatePaymentStatus(PaymentStatusDto paymentStatusDto){
		SaleOrder saleorder = saleOrderRepository.findByCode(paymentStatusDto.getSaleOrderCode());
		saleorder.setPaymentStatus(PaymentStatus.valueOf(paymentStatusDto.getPaymentStatus()));
		saleOrderRepository.save(saleorder);
	}

	public void rejectOrder(SaleOrder saleOrder){
		SaleOrder saleOrder2 = saleOrderRepository.findByCode(saleOrder.getCode());
		saleOrder2.setPaymentStatus(PaymentStatus.CANCELLED);
		saleOrder2.setReason(saleOrder.getReason());

		//Cập nhật số lượng sản phẩm nếu đơn bị hủy
		List<SaleorderProducts> saleorderProducts = saleorderProductsService.findBySaleOrderId(saleOrder2.getId());

		for (SaleorderProducts saleorderProduct : saleorderProducts) {
			Product product = saleorderProduct.getProduct(); // Lấy sản phẩm
			Integer quantity = saleorderProduct.getQuantity(); // Lấy số lượng sản phẩm
			if (product != null && quantity != null) {
				product.setQuantity(product.getQuantity() + quantity);
				productService.save(product);
			}
		}
		saleOrderRepository.save(saleOrder2);
	}

	public SaleOrder findById(int id) {
		return saleOrderRepository.findById(id).orElse(null);
	}

	public SaleOrderDto findByCode(String code) {
		return saleOrderRepository.findDtoByCode(code);
	}

	public ResponseEntity<Map<String, Object>> checkOrderCode(String code) {
		Map<String, Object> jsonResult = new HashMap<>();

		SaleOrder saleorder = saleOrderRepository.findByCode(code);
		if (saleorder == null ) {
			jsonResult.put("error", "Không có thông tin đơn hàng");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jsonResult);
		}
		List<SaleorderProducts> saleorderProductList = saleorderProductsService.findBySaleOrderId(saleorder.getId());
		if (saleorderProductList.isEmpty() ) {
			jsonResult.put("error", "Không có thông tin đơn hàng");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jsonResult);
		}
		else {
			StringBuilder messageBuilder = new StringBuilder();
			messageBuilder.append("Bạn đã mua ").append("các sản phẩm bao gồm : ");

			for (SaleorderProducts saleorderProducts : saleorderProductList) {
				Product product = productService.findById(saleorderProducts.getProduct().getId()).orElse(null);
				if (product == null) {
					jsonResult.put("error", "Không có thông tin mặt hàng");
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jsonResult);
				}else {
					// Thêm sản phẩm vào thông điệp mua hàng
					messageBuilder.append(saleorderProducts.getQuantity());
					messageBuilder.append(" sản phẩm ");
					messageBuilder.append(product.getTitle());
					messageBuilder.append(" và ");
				}
			}

			// Tạo một DecimalFormat cho định dạng số tiền
			DecimalFormat vndFormat = new DecimalFormat("#,### VNĐ");
			Double totalPrice = saleorder.getTotalPrice();
			String formattedTotalPrice = vndFormat.format(totalPrice);
			messageBuilder.append("<br>Tổng hóa đơn đơn hàng : ").append(formattedTotalPrice);

			if (saleorder.getPaymentStatus().equals(PaymentStatus.PENDING)) {
				messageBuilder.append("<br>Tình trạng đơn hàng của bạn : Chưa xử lý").append("<br>");
			} else if (saleorder.getPaymentStatus().equals(PaymentStatus.SHIPPING)) {
				messageBuilder.append("<br>Tình trạng đơn hàng của bạn : Đang giao hàng").append("<br>");
			} else if (saleorder.getPaymentStatus() == PaymentStatus.CANCELLED) {
				messageBuilder.append("<br>Tình trạng đơn hàng của bạn : Hủy đơn").append("<br>");
				messageBuilder.append("<br>Lý do hủy đơn : ").append(saleorder.getReason()).append("<br>");
			} else if(saleorder.getPaymentStatus() == PaymentStatus.DELIVERED){
				messageBuilder.append("<br>Tình trạng đơn hàng của bạn : Đã giao hàng").append("<br>");
			}

			jsonResult.put("success", messageBuilder.toString());

			return ResponseEntity.ok(jsonResult);
		}
	}


	@Transactional
	public SaleOrder save(User userLogined, SaleOrder saleOrder) {

		String userName = getUserName(userLogined);
		if (saleOrder.getId() == null) {
			// Tự động tạo/cập nhật mới ngày tạo theo ngày hiện tại
			saleOrder.setCreatedDate(LocalDateTime.now());
			saleOrder.setCreatedBy(userName);
			super.save(saleOrder); // thêm mới

			saleOrder.getSaleOrderProducts().
					forEach(saleorderProductsService::save
					);
			return saleOrder;
		} else {
			saleOrder.setUpdatedDate(LocalDateTime.now());
			saleOrder.setUpdatedBy(userName);
			return super.save(saleOrder); // cập nhật
		}
	}

	public String getUserName(User userLogined) {
		if(userLogined == null) {
			return "Guest";
		}else{
			return userLogined.getUsername();
		}
	}

	public Long count(){
		return saleOrderRepository.count();
	}

	public Integer getTotalItemsSold(){
		return saleOrderRepository.getTotalItemsSold();
	}

	public Double getTotalSalesValue(){
		return saleOrderRepository.getTotalSalesValue();
	}

}
