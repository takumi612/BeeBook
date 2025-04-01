package beebooks.repository;

import beebooks.entities.SaleorderProducts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SaleOrderProductRepository extends JpaRepository<SaleorderProducts, Integer> {

List<SaleorderProducts> findBySaleOrderId(int saleorderId);

Page<SaleorderProducts> findBySaleOrderId(int saleorderId, Pageable pageable);

@Query("SELECT sop FROM SaleorderProducts  sop JOIN sop.saleOrder so WHERE so.code = :saleOrderCode")
Page<SaleorderProducts> findBySaleOrderCode(@Param("saleOrderCode")String saleOrderCode, Pageable pageable);

}
