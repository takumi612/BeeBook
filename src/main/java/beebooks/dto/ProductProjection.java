package beebooks.dto;

import java.time.LocalDateTime;
import java.util.Date;

public interface ProductProjection {
    int getId();
    String getTitle();
    double getPrice();
    String getAvatar();
    Boolean getStatus();

    PromotionInfo getPromotion();

    interface PromotionInfo {
        String getName(); // Giả sử Promotion có trường name
        LocalDateTime getStartDate();
        LocalDateTime getEndDate();
        Double getPercent();
    }
}
