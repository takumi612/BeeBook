package beebooks.ultilities;

import beebooks.entities.Product;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CartItem {
	private int productId;
	private String productName;
	private int quantity;
	private String productPictures;
	private Double priceUnit;

	public void setProduct(Product product) {
		this.productName = product.getTitle();
		this.priceUnit = product.getPrice();
	}

	public double getItemTotalPrice() {
		return quantity * priceUnit;
	}

}
