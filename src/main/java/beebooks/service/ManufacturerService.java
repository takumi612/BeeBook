package beebooks.service;

import beebooks.specifications.ManufacturerSpecification;
import beebooks.ultilities.searchUtil.Search;
import beebooks.entities.Manufacturer;
import beebooks.repository.ManufacturerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class ManufacturerService extends BaseService<Manufacturer,Integer> {

	private final ManufacturerRepository manufacturerRepository;

    public ManufacturerService(ManufacturerRepository manufacturerRepository) {
        super(manufacturerRepository);
		this.manufacturerRepository = manufacturerRepository;
    }


    public Page<Manufacturer> search(Search searchModel) {
		Specification<Manufacturer> spec = ManufacturerSpecification.getManufacturerSpecification(searchModel);
		return manufacturerRepository.findAll(spec, searchModel.getPage());
	}

}
