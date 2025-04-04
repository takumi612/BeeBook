package beebooks.dto;

import beebooks.entities.SaleOrder;
import beebooks.entities.SaleorderProducts;
import beebooks.entities.enums.PaymentStatus;
import beebooks.entities.enums.PaymentType;
import lombok.Getter;
import lombok.Setter;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class SaleOrderDto {
    private Integer id;

    private String createBy;

    private String code;

    private String customerName;

    private String customerAddress;

    private String customerPhone;

    private String customerEmail;

    private Double totalPrice;//tổng giá trị đơn hàng

    private String reason;//lý do hủy đơn

    private LocalDateTime createdDate;

    private PaymentType paymentType;//1: thanh toán bằng tiền mặt, 2: thanh toán bằng vnpay

    private String paymentDisplayType;

    private PaymentStatus paymentStatus;//1: Chưa xử lý, 2: Đang giao hàng, 3: Đã giao hàng, 4: Đơn hàng đã bị hủy

    private String paymentDisplayStatus;

    private Set<SaleorderProducts> saleOrderProducts;

    public SaleOrderDto() {}

    public SaleOrderDto(String code, PaymentStatus paymentStatus,LocalDateTime createdDate,String customerAddress, String customerEmail, String customerName, String customerPhone, Double totalPrice, String reason ,PaymentType paymentType,String createBy) {
        this.code = code;
        this.createdDate = createdDate;
        this.customerAddress = customerAddress;
        this.customerEmail = customerEmail;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.totalPrice = totalPrice;
        this.reason = reason;
        this.createBy = createBy;
        this.paymentStatus = paymentStatus;
        this.paymentType = paymentType;

        this.paymentDisplayStatus = paymentStatus != null ? paymentStatus.getDisplayValue() : "";
        this.paymentDisplayType = paymentType != null ? paymentType.getDisplayValue() :"";
    }

    public SaleOrderDto(SaleOrder saleOrder) {
        this.createBy = saleOrder.getCreatedBy();
        this.code = saleOrder.getCode();
        this.customerName = saleOrder.getCustomerName();
        this.customerAddress = saleOrder.getCustomerAddress();
        this.customerPhone = saleOrder.getCustomerPhone();
        this.customerEmail = saleOrder.getCustomerEmail();
        this.totalPrice = saleOrder.getTotalPrice();
        this.reason = saleOrder.getReason();
        this.paymentType = saleOrder.getPaymentType();
        this.paymentStatus = saleOrder.getPaymentStatus();
        this.createdDate = saleOrder.getCreatedDate();
        this.saleOrderProducts = saleOrder.getSaleOrderProducts();

        this.paymentDisplayStatus = saleOrder.getPaymentStatus() != null ? paymentStatus.getDisplayValue() : "";
        this.paymentDisplayType = saleOrder.getPaymentType() != null ? paymentType.getDisplayValue() :"";

    }

    public Timestamp getCreatedTimestamp() {
        return (this.createdDate == null) ? null : Timestamp.valueOf(this.createdDate);
    }
}
