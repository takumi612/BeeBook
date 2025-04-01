package beebooks.specifications;

import beebooks.entities.Product;
import org.springframework.data.jpa.domain.Specification;

public interface BaseSpecification {
    private static Specification<Product> checkStatus(Boolean checkStatus) {
        if(!checkStatus) {
            return null;
        }else{
            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), 1);
        }
    }
}
