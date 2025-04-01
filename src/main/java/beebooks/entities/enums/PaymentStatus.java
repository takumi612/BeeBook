package beebooks.entities.enums;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    PENDING("Chờ xử lý") ,
    SHIPPING("Đang Giao Hàng"),
    DELIVERED("Đã Giao Hàng") ,
    CANCELLED("Đã Hủy");

    private final String displayValue;

    PaymentStatus(String displayValue) {
        this.displayValue = displayValue;
    }

}
