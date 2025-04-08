package beebooks.specifications;

import beebooks.ultilities.searchUtil.BlogSearch;
import beebooks.entities.Blog;
import beebooks.entities.Categories;
import beebooks.entities.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

public class BLogSpecification {
    public static Specification<Blog> getBlogSpecification(BlogSearch searchModel) {
        if (searchModel == null) {
            return null;
        }
        return Specification
                .where(categoryIdEquals(searchModel.getCategoryId()))
                .and(seoEquals(searchModel.getSeo()))
                .and(keywordLike(searchModel.getKeyword()));
    }

    private static Specification<Blog> categoryIdEquals(Integer categoryId) {
        return (root, query, criteriaBuilder) -> {
            if (categoryId == null || categoryId==0) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("categories").get("id"), categoryId);
        };
    }

    private static Specification<Blog> seoEquals(String seo) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(seo)) {
                return null;
            }
            return criteriaBuilder.equal(root.get("seo"), seo);
        };
    }

    private static Specification<Blog> keywordLike(String keyword) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(keyword)) {
                return null;
            }
            String likePattern = "%" + keyword + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(root.get("title"), likePattern),
                    criteriaBuilder.like(root.get("details"), likePattern),
                    criteriaBuilder.like(root.get("shortDes"), likePattern)
            );
        };
    }
}
