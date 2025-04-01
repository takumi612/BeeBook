package beebooks.specifications;

import beebooks.entities.Product;
import org.springframework.data.jpa.domain.Specification;

// Ý tưởng để thêm 1 spec default nhưng mà đang suwr dụng ít nên ko dùng tới
public interface BaseSpecification {
    private static Specification<Product> checkStatus(Boolean checkStatus) {
        if(!checkStatus) {
            return null;
        }else{
            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), 1);
        }
    }
}
