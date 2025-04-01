package beebooks.specifications;

import beebooks.ultilities.searchUtil.BlogSearch;
import beebooks.entities.Categories;
import beebooks.entities.CategoriesBlog;
import beebooks.entities.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

public class CategoriesBlogSpecification {
    public static Specification<CategoriesBlog> getCategoriesBlogSpecification(BlogSearch searchModel) {
        if (searchModel == null) {
            return null;
        }
        return Specification
                .where(hasCategoryId(searchModel.getCategoryId()))
                .and(hasSeo(searchModel.getSeo()));
    }

    // Cái này vô dụng ?
    private static Specification<CategoriesBlog> hasCategoryId(Integer categoryId) {
        return (root, query, criteriaBuilder) -> {
            if (categoryId == null) {
                return null;
            }
            Join<Product, Categories> categoriesJoin = root.join("categories", JoinType.INNER);
            return criteriaBuilder.equal(categoriesJoin.get("id"), categoryId);
        };
    }

    private static Specification<CategoriesBlog> hasSeo(String seo) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(seo)) {
                return null;
            }
            return criteriaBuilder.equal(root.get("seo"), seo);
        };
    }

}
