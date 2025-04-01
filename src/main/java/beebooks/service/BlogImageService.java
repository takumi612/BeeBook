package beebooks.service;

import beebooks.entities.BlogImage;
import beebooks.repository.BlogImageRepository;

import org.springframework.stereotype.Service;

@Service
public class BlogImageService extends BaseService<BlogImage,Integer> {

	BlogImageRepository blogImageRepository;

	public BlogImageService(BlogImageRepository blogImageRepository) {
		super(blogImageRepository);
		this.blogImageRepository = blogImageRepository;
	}

	public void deleteByBlogId(Integer id) {
		blogImageRepository.deleteByBlogId(id);
    }
}
