package beebooks.service;

import beebooks.entities.Images;
import beebooks.repository.ImageRepository;
import org.springframework.stereotype.Service;

@Service
public class ImageService extends BaseService<Images,Integer> {
	ImageRepository repository;

	public ImageService(ImageRepository repository) {
		super(repository);
		this.repository = repository;
	}

	public void deleteByPath(String path) {
		repository.deleteProductImageByPath(path);
	}
}
