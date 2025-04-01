package beebooks.service;

import beebooks.specifications.PromotionSpecification;
import beebooks.ultilities.searchUtil.Search;
import beebooks.entities.Promotion;
import beebooks.repository.PromotionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class PromotionService extends BaseService<Promotion,Integer> {

	private final PromotionRepository promotionRepository;

    public PromotionService(PromotionRepository promotionRepository) {
        super(promotionRepository);
		this.promotionRepository = promotionRepository;
    }

	public Page<Promotion> search(Search searchModel){
		Specification<Promotion> spec = PromotionSpecification.getPromotionSpecification(searchModel);
		return promotionRepository.findAll(spec,searchModel.getPage());
	}

}
