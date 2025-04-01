package beebooks.service;

import beebooks.entities.Categories;
import beebooks.repository.CategoriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CategoriesService extends BaseService<Categories,Integer> {

	private final CategoriesRepository categoriesRepository;

    public CategoriesService(CategoriesRepository categoriesRepository) {
		super(categoriesRepository);
        this.categoriesRepository = categoriesRepository;
    }

	public Optional<Categories> findById(int id) {
		return categoriesRepository.findById(id);
	}

	public List<Categories> searchALl(){
		return categoriesRepository.findAll();
	}
}
