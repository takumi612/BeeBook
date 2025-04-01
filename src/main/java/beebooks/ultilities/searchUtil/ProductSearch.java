package beebooks.ultilities.searchUtil;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter
@Setter
public class ProductSearch extends BaseSearch {

	public String keyword;

	public Integer categoryId;

	public String seo;

	public String productStatus;

	public ProductSearch(){
		this.productStatus = "1";
		this.sortBy = "quantity";
	}


}
