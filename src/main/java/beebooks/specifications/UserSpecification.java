package beebooks.specifications;

import beebooks.ultilities.searchUtil.UserSearch;
import beebooks.entities.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class UserSpecification {
    public static Specification<User> getUserSpecification(UserSearch searchModel) {
        return Specification.where(searchByModel(searchModel));
    }

    public static Specification<User> searchByModel(UserSearch searchModel) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (searchModel != null && !StringUtils.isEmpty(searchModel.getKeyword())) {
                String keyword = searchModel.getKeyword().toLowerCase();

                Predicate keywordPredicate = criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("username")), "%" + keyword + "%"),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + keyword + "%")
                );
                predicates.add(keywordPredicate);
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
