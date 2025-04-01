package beebooks.repository;

import beebooks.dto.SaleOrderDto;
import beebooks.entities.SaleOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SaleOrderRepository extends JpaRepository<SaleOrder, Integer>, JpaSpecificationExecutor<SaleOrder> {

    long count();

    @Override
    @EntityGraph(attributePaths = {"saleOrderProducts","products"})
    Page<SaleOrder> findAll(Specification specification, Pageable pageable);

    SaleOrder findByCode(String code);

    @Query("SELECT NEW beebooks.dto.SaleOrderDto(so.code,so.paymentStatus,so.createdDate,so.customerAddress,so.customerEmail,so.customerName,so.customerPhone,so.totalPrice,so.reason,so.paymentType,so.createdBy) FROM SaleOrder so WHERE so.code = :code")
    SaleOrderDto findDtoByCode(@Param("code") String code);

    @Query("SELECT SUM(item.quantity) FROM SaleOrder so JOIN so.saleOrderProducts item WHERE so.paymentStatus != 4")
    Integer getTotalItemsSold();

    @Query("SELECT SUM(so.totalPrice) FROM SaleOrder so")
    Double getTotalSalesValue();
}
