package beebooks.service;

import beebooks.specifications.ContactSpecification;
import beebooks.entities.Contact;
import beebooks.ultilities.searchUtil.Search;
import beebooks.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContactService extends BaseService<Contact,Integer> {

	private final ContactRepository repository;

	public ContactService(ContactRepository repository) {
		super(repository);
		this.repository = repository;
	}

	public Page<Contact> search(Search searchModel){
		Specification<Contact> spec = ContactSpecification.searchByModel(searchModel);
		return repository.findAll(spec,searchModel.getPage());
	}

	@Transactional
	public Contact checkEmailContact(Contact entityContact) {
		return repository.findByEmail(entityContact.getEmail());
	}

	public void deleteById(Integer id){
		repository.deleteById(id);
	}
}
