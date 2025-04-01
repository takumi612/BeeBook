package beebooks.specifications;

import beebooks.ultilities.searchUtil.Search;
import beebooks.entities.Author;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class AuthorSpecification {
    public static Specification<Author> getAuthorSpecification(Search searchModel) {
        if (searchModel == null) {
            return null;
        }
        return Specification
                .where(authorIdEquals(searchModel.getId()))
                .and(keywordLike(searchModel.getKeyword()));
    }

    // Tìm kiếm dựa trên authorID
    private static Specification<Author> authorIdEquals(Integer authorId) {
        return (root, query, criteriaBuilder) -> {
            if (authorId == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("id"), authorId);
        };
    }

    // Tìm kiếm dựa trên name và biography
    private static Specification<Author> keywordLike(String keyword) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(keyword)) {
                return null;
            }
            String likePattern = "%" + keyword + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(root.get("name"), likePattern),
                    criteriaBuilder.like(root.get("biography"), likePattern)
            );
        };
    }
}
