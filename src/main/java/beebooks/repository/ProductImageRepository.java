package beebooks.repository;

import beebooks.entities.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Integer>{
    @Modifying
    @Transactional
    void deleteProductImageByProductId(Integer id);
}
