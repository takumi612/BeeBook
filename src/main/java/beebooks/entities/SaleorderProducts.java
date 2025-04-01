package beebooks.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name="saleorder_products")
public class SaleorderProducts extends BaseEntity{
	@Column(name = "quantity", nullable = false)
	private Integer quantity;

	@Column(name = "total")
	private Double total;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "saleorder_id")
	private SaleOrder saleOrder;

}
