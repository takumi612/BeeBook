package beebooks.service;

import beebooks.dto.BlogDto;
import beebooks.specifications.BLogSpecification;
import beebooks.ultilities.searchUtil.BlogSearch;
import beebooks.entities.Blog;
import beebooks.entities.Images;
import beebooks.repository.BlogRepository;
import beebooks.ultilities.ResultUtil;
import com.github.slugify.Slugify;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import lombok.extern.slf4j.Slf4j;


import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;


@Slf4j
@Service
public class BlogService extends BaseService<Blog,Integer> {
    private final ProductService productService;
    private final BlogRepository blogRepository;
    private final CategoriesBlogService categoriesBlogService;

    @Value("${app.upload.dir:${user.home}}")
    private String uploadDir;

    @Value("${app.upload.default")
    private String defaultPath;

    public BlogService(ProductService productService,BlogRepository blogRepository, CategoriesBlogService categoriesBlogService) {
        super(blogRepository);
        this.categoriesBlogService = categoriesBlogService;
        this.productService = productService;
        this.blogRepository = blogRepository;
    }


    public List<Blog> findBySeo(String seo){
        return blogRepository.findBySeo(seo);
    }


    public Page<Blog> search(BlogSearch searchModel) {
        Specification<Blog> spec = BLogSpecification.getBlogSpecification(searchModel);
        return blogRepository.findAll(spec,searchModel.getPage());
    }

    @Transactional
    public Blog save(BlogDto blogDto)
            throws IllegalStateException, IOException {

        Blog blog = transferDtoToBlog(blogDto);
        MultipartFile productAvatar = blogDto.getProductAvatar();

        // có đẩy avartar ??? => xóa avatar cũ đi và thêm avatar mới
        if (productAvatar != null && !productAvatar.isEmpty()) {
            if (blogDto.getAvatar() != null) {
                deleteFile(blogDto.getAvatar());
            }
            // Save new avatar
            String avatarPath = productService.saveFile(productAvatar, "avatar");
            blog.setAvatar(avatarPath);
        } else {
            if(blogDto.getAvatar()!=null && !blogDto.getAvatar().isEmpty()){
                blog.setAvatar(blogDto.getAvatar());
            }else {
                blog.setAvatar(defaultPath);
            }
        }
//        // có đẩy pictures ???
//        if (productPictures != null && productPictures.length > 0) {
//            // Delete existing blog images
//            if (blogDto.getImages() != null) {
//                for (Images path : blogDto.getImages()) {
//                    deleteFile(path.getPath());
//                    blogImageService.deleteByBlogId(blog.getId());
//                }
//            }
//            // Save new blog pictures
//            savePictures(blog, productPictures);
//        }else if(blogDto.getImages()!=null && !blogDto.getImages().isEmpty()){
//            blog.setImages(blogDto.getImages());
//        }
        blog.setSeo(new Slugify().slugify(blog.getTitle()));
        return super.save(blog);
    }

    // Helper method to delete file
    private void deleteFile(String filePath) {
        try {
            Path path = Paths.get(uploadDir, filePath);
            Files.deleteIfExists(path);
        } catch (IOException e) {
            log.error("Error deleting file: {}", filePath, e);
        }
    }
    private void savePictures(Blog blog,MultipartFile[] productPictures){
        Arrays.stream(productPictures)
                .filter(pic -> pic != null && !pic.isEmpty())
                .forEach(pic -> {
                    try{
                        String picturePath = productService.saveFile(pic, "pictures");
                        Images pi = new Images();
                        pi.setPath(picturePath);
                        pi.setTitle(pic.getOriginalFilename());
//                        blog.addBlogImage(pi);
                    }catch (IOException e){
                        log.error("Error saving productPicture");
                    }
                });
    }

    public ResultUtil deleteBlog(int id){
        ResultUtil resultUtil;
        Blog blog = blogRepository.findById(id).orElse(null);
        if(blog != null) {
            blog.setStatus(false);
            blogRepository.save(blog);
            resultUtil = new ResultUtil("successMessage","Xóa sản phẩm thành công");
        }else{
            resultUtil = new ResultUtil("errorMessage","Không tìm thấy sản phẩm trong CSDL");
        }
        return resultUtil;
    }

    private Blog transferDtoToBlog(BlogDto blogDto) {
        Blog blog = new Blog(blogDto);
        blog.setCategoriesBlog(categoriesBlogService.findById(blogDto.getCategoryId()).orElse(null));
        return blog;
    }

}
