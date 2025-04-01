package beebooks.service;

import beebooks.specifications.CategoriesBlogSpecification;
import beebooks.ultilities.searchUtil.BlogSearch;
import beebooks.entities.CategoriesBlog;
import beebooks.repository.CategoriesBlogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoriesBlogService extends BaseService<CategoriesBlog, Integer> {

	CategoriesBlogRepository repository;

	public CategoriesBlogService(CategoriesBlogRepository repository) {
		super(repository);
		this.repository = repository;
	}


	public Page<CategoriesBlog> search(BlogSearch searchModel) {
		Specification<CategoriesBlog> spec = CategoriesBlogSpecification.getCategoriesBlogSpecification(searchModel);
		return repository.findAll(spec,searchModel.getPage());
	}

	public List<CategoriesBlog> findAll() {
		return repository.findAll();
	}

}
