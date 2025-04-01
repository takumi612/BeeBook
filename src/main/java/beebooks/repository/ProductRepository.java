package beebooks.repository;

import beebooks.dto.ProductProjection;
import beebooks.entities.Categories;
import beebooks.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> , JpaSpecificationExecutor<Product> {

    @Override
    @EntityGraph(attributePaths = {"author","manufacturer","promotion","categories"})
    Page<Product> findAll(Specification<Product> specification,Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.status = true  AND LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) ")
    Page<ProductProjection> findByTitleLikeIgnoreCase(@Param("keyword")String title, Pageable pageable);

    List<ProductProjection> findByTitleLikeIgnoreCase(String title);

    Page<ProductProjection> findAllProjectedByStatus(Boolean status, Pageable pageable);

    Page<ProductProjection> findAllProjectedByCategoriesIdAndStatus(Integer categoryId, Boolean status, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.categories.id = :categoryId")
    List<ProductProjection> findByCategoriesId(@Param("categoryId") Integer categoryId);
}
