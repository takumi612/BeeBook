package beebooks.entities.enums;

import lombok.Getter;

@Getter
public enum PaymentType {
    ONLINE("Thanh toán online"),
    OFFLINE("Thanh toán offline");

    private final String displayValue;

    PaymentType(String displayValue) {
        this.displayValue = displayValue;
    }

}
