package beebooks.service;

import beebooks.ultilities.searchUtil.OrderSearch;
import beebooks.entities.SaleorderProducts;
import beebooks.repository.SaleOrderProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class SaleOrderProductsService extends BaseService<SaleorderProducts,Integer> {

	private final SaleOrderProductRepository repository;

    public SaleOrderProductsService(SaleOrderProductRepository repository) {
		super(repository);
        this.repository = repository;
    }

	public Page<SaleorderProducts> getByOrderId(OrderSearch searchModel) {
		return repository.findBySaleOrderId(searchModel.getId(),searchModel.getPage());
	}

	public Page<SaleorderProducts> getByOrderCode(OrderSearch searchModel) {
		return repository.findBySaleOrderCode(searchModel.getCode(),searchModel.getPage());
	}

	public void deleteAll(Set<SaleorderProducts> saleorderProducts){
		repository.deleteAll(saleorderProducts);
	}

	public List<SaleorderProducts> findBySaleOrderId(int saleOrderId) {
		return repository.findBySaleOrderId(saleOrderId);
	}
}
