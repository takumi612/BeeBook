package beebooks.service;

import beebooks.specifications.SubscribeSpecification;
import beebooks.ultilities.searchUtil.Search;
import beebooks.entities.Subscribe;
import beebooks.repository.SubscribeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

public class SubscribeService extends BaseService<Subscribe,Integer> {

	private final SubscribeRepository subscribeRepository;

    public SubscribeService(SubscribeRepository subscribeRepository) {
		super(subscribeRepository);
        this.subscribeRepository = subscribeRepository;
    }

    public Page<Subscribe> search(Search searchModel) {
		Specification<Subscribe> spec = SubscribeSpecification.getSubscribeSpecification(searchModel);
		return subscribeRepository.findAll(spec,searchModel.getPage());
	}

	@Transactional
	public Subscribe checkEmailSubscribe(Subscribe entity) {
		return subscribeRepository.findByEmail(entity.getEmail());
	}

	public void deleteById(int id) {
		subscribeRepository.deleteById(id);
	}
}
