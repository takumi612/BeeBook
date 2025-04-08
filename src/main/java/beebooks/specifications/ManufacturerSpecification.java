package beebooks.specifications;

import beebooks.ultilities.searchUtil.Search;
import beebooks.entities.Manufacturer;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class ManufacturerSpecification {
    public static Specification<Manufacturer> getManufacturerSpecification(Search searchModel) {
        if (searchModel == null) {
            return null;
        }
        return Specification
                .where(manufacturerIdEquals(searchModel.getId()))
                .and(keywordLike(searchModel.getKeyword()));
    }

    private static Specification<Manufacturer> manufacturerIdEquals(Integer manufacturerId) {
        return (root, query, criteriaBuilder) -> {
            if (manufacturerId == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("id"), manufacturerId);
        };
    }

    private static Specification<Manufacturer> keywordLike(String keyword) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(keyword)) {
                return criteriaBuilder.conjunction();
            }
            String likePattern = "%" + keyword + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(root.get("name"), likePattern),
                    criteriaBuilder.like(root.get("address"), likePattern)
            );
        };
    }
}
