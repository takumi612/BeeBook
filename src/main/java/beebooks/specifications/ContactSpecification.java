package beebooks.specifications;

import beebooks.entities.Contact;
import beebooks.ultilities.searchUtil.Search;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class ContactSpecification {
    public static Specification<Contact> searchByModel(Search searchModel) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (searchModel != null && !StringUtils.isEmpty(searchModel.getKeyword())) { // Sử dụng getKeyword() thay vì truy cập trực tiếp
                String keyword = searchModel.getKeyword().toLowerCase(); // Chuyển keyword về lowercase để tìm kiếm không phân biệt hoa thường

                Predicate keywordPredicate = criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + keyword + "%"),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + keyword + "%"),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("massage")), "%" + keyword + "%")
                 );
                predicates.add(keywordPredicate);
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
