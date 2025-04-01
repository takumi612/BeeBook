package beebooks.service;

import beebooks.specifications.AuthorSpecification;
import beebooks.ultilities.searchUtil.Search;
import beebooks.entities.Author;
import beebooks.repository.AuthorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service

public class AuthorService extends BaseService<Author,Integer> {

    private final AuthorRepository authorRepository;

	public AuthorService(AuthorRepository authorRepository) {
		super(authorRepository);
		this.authorRepository = authorRepository;
	}

	public Page<Author> search(Search searchModel){
		Specification<Author> spec = AuthorSpecification.getAuthorSpecification(searchModel);
		return authorRepository.findAll(spec,searchModel.getPage());
	}
}
