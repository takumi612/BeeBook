package beebooks.entities;

import beebooks.dto.CustomerDto;
import beebooks.entities.enums.PaymentStatus;
import beebooks.entities.enums.PaymentType;
import beebooks.ultilities.StringUtil;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "saleorder")
public class SaleOrder extends BaseEntity {
	@Column(name = "code", length = 45, nullable = false)
	private String code;

	@Column(name = "customer_name", length = 100)
	private String customerName;

	@Column(name = "customer_address", length = 100)
	private String customerAddress;

	@Column(name = "customer_phone", length = 100)
	private String customerPhone;

	@Column(name = "customer_email", length = 100)
	private String customerEmail;

	@Column(name = "total_price")
	private Double totalPrice;//tổng giá trị đơn hàng

	@Column(name = "reason")
	private String reason;//lý do hủy đơn

	@Column(name = "payment_type")
	@Enumerated(EnumType.STRING)
	private PaymentType paymentType;//1: thanh toán bằng tiền mặt, 2: thanh toán bằng vnpay

	// ENUM('PENDING', 'SHIPPING', 'DELIVERED', 'CANCELLED')
	@Column(name = "payment_status")
	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus;

	@OneToMany(mappedBy = "saleOrder",fetch = FetchType.LAZY)
	private Set<SaleorderProducts> saleOrderProducts = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "saleorder_products", joinColumns = @JoinColumn(name = "saleorder_id")
			, inverseJoinColumns = @JoinColumn(name = "product_id"))
	private Set<Product> products = new HashSet<>();

	public SaleOrder() {}

	public SaleOrder(CustomerDto customerDto){
		this.customerName = customerDto.getCustomerName();
		this.customerEmail = customerDto.getCustomerEmail();
		this.customerAddress = customerDto.getCustomerAddress();
		this.customerPhone = customerDto.getCustomerPhone();
		this.code = StringUtil.generateOrderCode();
		this.paymentType = PaymentType.OFFLINE;
		this.paymentStatus = PaymentStatus.PENDING;
	}

    public void addSaleOrderProducts(SaleorderProducts saleOrderProducts) {
		saleOrderProducts.setSaleOrder(this);
		this.saleOrderProducts.add(saleOrderProducts);
	}

}
