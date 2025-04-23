package beebooks.service;


import beebooks.entities.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract class BaseService<T extends BaseEntity, ID extends Serializable> {

	protected final JpaRepository<T, ID> repository;

	public BaseService(JpaRepository<T, ID> repository) {
		this.repository = repository;
	}

	public List<T> findAll() {
		return repository.findAll();
	}

	public Page<T> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public Optional<T> findById(ID id) {
		return repository.findById(id);
	}

	public T save(T entity) {
		return repository.save(entity);
	}

	public void deleteById(ID id) {
		Optional<T> entityOptional = repository.findById(id);
		entityOptional.ifPresent(entity -> {
			entity.setStatus(false);
			repository.save(entity);
		});
	}
}
