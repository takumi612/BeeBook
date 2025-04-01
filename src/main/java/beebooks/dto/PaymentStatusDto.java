package beebooks.dto;

import beebooks.entities.enums.PaymentStatus;
import lombok.Data;

@Data
public class PaymentStatusDto {
    private Integer saleOrderId;
    private String saleOrderCode;
    private String paymentStatus;
}
