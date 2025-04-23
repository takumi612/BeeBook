package beebooks.specifications;

import beebooks.entities.enums.PaymentStatus;
import beebooks.ultilities.searchUtil.OrderSearch;
import beebooks.entities.Product;
import beebooks.entities.SaleOrder;
import beebooks.entities.SaleorderProducts;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

public class SaleOrderSpecification {
    public static Specification<SaleOrder> getSaleOrderSpecification(OrderSearch searchModel) {
        return Specification.where(searchByKeyword(searchModel))
                .and(hasUser(searchModel.getCreateBy()))
                .and(dateRangeBetween(searchModel.getStartDate(), searchModel.getEndDate()))
                .and(searchByStatus(searchModel.getOrderStatus()));
    }

    // Tìm kiếm dựa trên keyword
    public static Specification<SaleOrder> searchByKeyword(OrderSearch searchModel) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (searchModel != null && !StringUtils.isEmpty(searchModel.getKeyword())) {
                String keyword = searchModel.getKeyword().toLowerCase(); // Chuyển keyword về lowercase để tìm kiếm không phân biệt hoa thường
                if (keyword.trim().isEmpty()) {
                    return criteriaBuilder.conjunction(); // Trả về true nếu keyword rỗng, không lọc gì thêm
                }
                Predicate keywordPredicate;
                String likePattern = "%" + keyword + "%";
                // Tìm kiếm theo name, email, phone, SaleOrderCode, address và product title
                Join<SaleOrder, SaleorderProducts> saleOrderProductJoin = root.join("saleOrderProducts", JoinType.INNER);
                Join<SaleorderProducts, Product> productJoin = saleOrderProductJoin.join("product", JoinType.INNER);

                keywordPredicate = criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("customerName")), likePattern),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("customerEmail")), likePattern),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("customerPhone")), likePattern),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("code")), likePattern),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("customerAddress")), likePattern),
                        criteriaBuilder.like(
                                criteriaBuilder.lower(productJoin.get("title")),
                                "%" + keyword.toLowerCase() + "%"
                        )
                );
                predicates.add(keywordPredicate);
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    // Tìm kiếm dựa theo ngày
    public static Specification<SaleOrder> dateRangeBetween(LocalDate startDate, LocalDate endDate) {
        // Do entity muốn sử dụng LocalDateTime  nhưng giao diện trả về LocalDate nên đổi lại thành LocalDateTime
        LocalDateTime startDateTime = startDate != null ?
                startDate.atStartOfDay() : null;

        LocalDateTime endDateTime = endDate != null ?
                endDate.plusDays(1).atStartOfDay().minusNanos(1) : null;

        return (root, query, criteriaBuilder) -> {
            List <Predicate> predicates = new ArrayList<>();
            if (startDateTime != null && endDateTime != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdDate"), startDateTime));
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdDate"), endDateTime));
            }else{
                if(startDateTime != null){
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdDate"), startDateTime));
                }
                if(endDateTime != null){
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdDate"), endDateTime));
                }
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    // Tìm kiếm dựa trên trạng thái thanh toán
    public static Specification<SaleOrder> searchByStatus(String status) {
        return (root, query, criteriaBuilder) ->
        {
                if(status == null || status.isEmpty()){
                    return criteriaBuilder.conjunction();
                }
                PaymentStatus paymentStatus = PaymentStatus.valueOf(status);
                return criteriaBuilder.equal(root.get("paymentStatus"), paymentStatus);
        };
    }

    // Tìm đơn hàng theo userId
    public static Specification<SaleOrder> hasUser(String userName) {
        return (root, query, criteriaBuilder) -> {
            if (userName == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("createdBy"),userName);
        };
    }

}

