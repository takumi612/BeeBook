package beebooks.specifications;

import beebooks.ultilities.searchUtil.ProductSearch;
import beebooks.entities.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class ProductSpecification {
    public static Specification<Product> getProductSpecification(ProductSearch searchModel) {
        if (searchModel == null) {
            return null;
        }
        return Specification
                .where(categoryIdEquals(searchModel.getCategoryId()))
                .and(productIdEquals(searchModel.getId()))
                .and(seoEquals(searchModel.getSeo()))
                .and(keywordLike(searchModel.getKeyword()))
                .and(searchByStatus(searchModel.getProductStatus()));
    }

    private static Specification<Product> categoryIdEquals(Integer categoryId) {
        return (root, query, criteriaBuilder) -> {
            if (categoryId == null|| categoryId==0) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("categories").get("id"), categoryId);
        };
    }

    private static Specification<Product> productIdEquals(Integer productId) {
        return (root, query, criteriaBuilder) -> {
            if (productId == null || productId == 0) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("id"), productId);
        };
    }

    private static Specification<Product> searchByStatus(String status) {
        return (root, query, criteriaBuilder) ->{
            try{
                int statusValue = Integer.parseInt(status);
                return criteriaBuilder.equal(root.get("status"), statusValue);
            }catch (NumberFormatException e){
                return criteriaBuilder.conjunction();
            }
        };
    }

    private static Specification<Product> seoEquals(String seo) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(seo)) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("seo"), seo);
        };
    }

    private static Specification<Product> keywordLike(String keyword) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(keyword)) {
                return criteriaBuilder.conjunction();
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
