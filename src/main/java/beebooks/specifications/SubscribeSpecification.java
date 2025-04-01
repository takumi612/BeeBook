package beebooks.specifications;

import beebooks.ultilities.searchUtil.Search;
import beebooks.entities.Subscribe;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class SubscribeSpecification {
    public static Specification<Subscribe> getSubscribeSpecification(Search searchModel) {
        if (searchModel == null) {
            return null;
        }
        return Specification
                .where(keywordLike(searchModel.getKeyword()));
    }


    private static Specification<Subscribe> keywordLike(String keyword) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(keyword)) {
                return null;
            }
            String likePattern = "%" + keyword + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(root.get("email"), likePattern)
            );
        };
    }
}
