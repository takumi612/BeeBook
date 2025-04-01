package beebooks.ultilities;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@ToString
public class Cart {
	
	private List<CartItem> cartItems = new ArrayList<CartItem>();

	public double getTotalPrice() {
		return cartItems.stream()
				.filter(Objects::nonNull)
				.map(CartItem::getItemTotalPrice)
				.reduce(0.0, Double::sum);
	}


}
