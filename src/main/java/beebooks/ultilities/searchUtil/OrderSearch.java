package beebooks.ultilities.searchUtil;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Data
public class OrderSearch extends BaseSearch {

	public String createBy;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public LocalDate startDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public LocalDate endDate;
	public String orderStatus;
	public String code;


	public OrderSearch() {
		this.sortBy = "createdDate";
		this.pageSize = 15;
	}
}
