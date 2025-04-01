package beebooks.specifications;

import beebooks.ultilities.searchUtil.Search;
import beebooks.entities.Promotion;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class PromotionSpecification {
    public static Specification<Promotion> getPromotionSpecification(Search searchModel) {
        if (searchModel == null) {
            return null;
        }
        return Specification
                .where(promotionIdEquals(searchModel.getId()))
                .and(keywordLike(searchModel.getKeyword()));
    }

    private static Specification<Promotion> promotionIdEquals(Integer manufacturerId) {
        return (root, query, criteriaBuilder) -> {
            if (manufacturerId == null || manufacturerId == 0) {
                return null;
            }
            return criteriaBuilder.equal(root.get("id"), manufacturerId);
        };
    }

    private static Specification<Promotion> keywordLike(String keyword) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(keyword)) {
                return null;
            }
            String likePattern = "%" + keyword + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(root.get("name"), likePattern)
            );
        };
    }
}
