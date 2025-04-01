package beebooks.ultilities.searchUtil;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;

@Data
@ToString
public abstract class BaseSearch {
	protected Integer id;
	protected int pageNumber = 0, pageSize = 20;
	protected String keyword;
	protected String sortBy = "id";
	protected String sortDirection = "asc";

	public Pageable getPage(){
		Sort sort = Sort.by(Sort.Direction.fromString(this.sortDirection), this.sortBy);
		return PageRequest.of(this.pageNumber, this.pageSize,sort);
	}
}
