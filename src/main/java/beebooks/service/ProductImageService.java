package beebooks.service;

import beebooks.entities.ProductImage;
import beebooks.repository.ProductImageRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductImageService extends BaseService<ProductImage,Integer> {
	ProductImageRepository repository;

	public ProductImageService(ProductImageRepository repository) {
		super(repository);
		this.repository = repository;
	}

	public void deleteByProductId(Integer id) {
		repository.deleteProductImageByProductId(id);
	}
}
