package beebooks.service;

import beebooks.dto.ProductDetailDto;
import beebooks.dto.ProductProjection;
import beebooks.entities.Categories;
import beebooks.ultilities.Cart;
import beebooks.ultilities.CartItem;
import beebooks.ultilities.ResultUtil;
import beebooks.ultilities.searchUtil.ProductSearch;
import beebooks.entities.Product;
import beebooks.entities.ProductImage;
import beebooks.specifications.ProductSpecification;
import beebooks.repository.ProductRepository;
import com.github.slugify.Slugify;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Slf4j
@Service
public class ProductService extends BaseService<Product,Integer> {

	private final CategoriesService categoriesService;
	private final AuthorService authorService;
	private final ManufacturerService manufacturerService;
	private final PromotionService promotionService;
	private final ProductImageService productImageService;
	private final ProductRepository productRepository;

	@Value("${app.upload.dir:${user.home}}")
	private String uploadDir;

	@Value("${app.upload.default")
	private String defaultPath;

    public ProductService(CategoriesService categoriesService, AuthorService authorService, ManufacturerService manufacturerService, PromotionService promotionService, ProductImageService productImageService, ProductRepository productRepository) {
        super(productRepository);
        this.categoriesService = categoriesService;
        this.authorService = authorService;
        this.manufacturerService = manufacturerService;
        this.promotionService = promotionService;
        this.productImageService = productImageService;
        this.productRepository = productRepository;
    }


    public Product findProductById(int id) {
		Optional<Product> product = productRepository.findById(id);
		return product.orElse(null);
	}

	public Page<ProductProjection> findProductByTitle(ProductSearch search) {
		return productRepository.findByTitleLikeIgnoreCase(search.getKeyword(),search.getPage());
	}
	public List<ProductProjection> findProductByTitle(String title) {
		return productRepository.findByTitleLikeIgnoreCase(title);
	}

	public Page<ProductDetailDto> search(ProductSearch searchModel) {
		Specification<Product> spec = ProductSpecification.getProductSpecification(searchModel);
		Page<Product> productPage = productRepository.findAll(spec,searchModel.getPage());
        return productPage.map(ProductDetailDto::new);
	}


	public Page<ProductProjection> findAllToDto(Pageable pageable){
		return productRepository.findAllProjectedByStatus(true,pageable);
	}

	public Page<ProductProjection> findByCategories(ProductSearch searchModel){
		return productRepository.findAllProjectedByCategoriesIdAndStatus(searchModel.getCategoryId(),true,searchModel.getPage());
    }

	public Optional<Product> findById(int id) {
		return productRepository.findById(id);
	}

	@Transactional
	public ResultUtil addProduct( ProductDetailDto productDetailDto) throws IOException {

		boolean isNewProduct = (productDetailDto.getId() == null);
		boolean titleExists = !findProductByTitle(productDetailDto.getTitle()).isEmpty();

		if (isNewProduct && titleExists) {
			return new ResultUtil("errorMessage", "Sách đã có trong CSDL");
		}
		// Xử lý thêm mới hoặc cập nhật
		save(productDetailDto);
		return new ResultUtil("successMessage", "Thành công lưu sách");
	}

	@Transactional
	public Product save(ProductDetailDto productDetailDto)
			throws IllegalStateException, IOException {

		Product product = transferDtoToProduct(productDetailDto);
		MultipartFile productAvatar = productDetailDto.getProductAvatar();
		MultipartFile[] productPictures =productDetailDto.getProductPictures();


		// có đẩy avartar ??? => xóa avatar cũ đi và thêm avatar mới
		if (productAvatar != null && !productAvatar.isEmpty()) {
			if (productDetailDto.getAvatar() != null && !productDetailDto.getAvatar().isEmpty()) {
				deleteFile(productDetailDto.getAvatar());
			}
			// Save new avatar
			String avatarPath = saveFile(productAvatar, "avatar");
			product.setAvatar(avatarPath);
		} else {
			if(productDetailDto.getAvatar()!=null && !productDetailDto.getAvatar().isEmpty()){
				product.setAvatar(productDetailDto.getAvatar());
			}else {
				product.setAvatar(defaultPath);
			}
		}
		// có đẩy pictures ???
		if (productPictures != null && productPictures.length > 0) {
			// Delete existing product images
			if (productDetailDto.getProductImage() != null && !productDetailDto.getProductImage().isEmpty()) {
				for (ProductImage path : productDetailDto.getProductImage()) {
					deleteFile(path.getPath());
					productImageService.deleteByProductId(product.getId());
				}
			}
			// Save new product pictures
			savePictures(product, productPictures);
		}else if(productDetailDto.getProductImage()!=null && !productDetailDto.getProductImage().isEmpty()){
			product.setProductImage(productDetailDto.getProductImage());
		}
		product.setSeo(new Slugify().slugify(product.getTitle()));
		return super.save(product);
	}

	@Transactional
	public Boolean deleteProduct(int id) {
		Product product = productRepository.findById(id).orElse(null);
		if(product != null) {
			product.setStatus(false);
			productRepository.save(product);
			return true;
		}else{
			return false;
		}
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
	private void savePictures(Product product,MultipartFile[] productPictures){
		Arrays.stream(productPictures)
				.filter(pic -> pic != null && !pic.isEmpty())
				.forEach(pic -> {
					try{
						String picturePath = saveFile(pic, "pictures");
						ProductImage pi = new ProductImage();
						pi.setPath(picturePath);
						pi.setTitle(pic.getOriginalFilename());
						product.addProductImage(pi);
					}catch (IOException e){
						log.error("Error saving productPicture");
					}
				});
	}

	public ResponseEntity<Map<String, Object>> updateQuantityCart(HttpServletRequest request,CartItem cartItem,String action) {
		Map<String, Object> jsonResult = new HashMap<>();
		HttpSession session = request.getSession();
		Cart cart;
		cart = (Cart) session.getAttribute("cart");

		List<CartItem> cartItems = cart.getCartItems();
		Optional<CartItem> foundItem =cartItems.stream()
				.filter(item -> item.getProductId() == cartItem.getProductId())
				.findFirst();

		if (foundItem.isPresent()) {
			CartItem item =foundItem.get();
			Product product = findProductById(item.getProductId());
			if(action.equals("add")){
				if(item.getQuantity()< product.getQuantity()){
					item.setQuantity(item.getQuantity() + 1);
				}
				else{
					item.setQuantity(item.getQuantity());
					jsonResult.put("error", "Vật phẩm không đủ số lượng ");
				}
			}
			else if(action.equals("minus")){
				if(item.getQuantity()>1){
					item.setQuantity(item.getQuantity() - 1);
				}else{
					item.setQuantity(1);
				}
			}

			jsonResult.put("status", "TC");
			jsonResult.put("itemPrice", item.getPriceUnit()*item.getQuantity());
			jsonResult.put("totalPrice",cart.getTotalPrice());
			jsonResult.put("currentProductQuantity", item.getQuantity());
		}

		session.setAttribute("cart",cart);
		return ResponseEntity.ok(jsonResult);
	}

	public String saveFile(MultipartFile file,String type) throws IOException {
		if(file==null||file.isEmpty()){
			return null;
		}
		Path uploadPath = Paths.get(uploadDir,"product", type);
		Files.createDirectories(uploadPath);

		// Generate unique filename with timestamp
		String originalFilename = file.getOriginalFilename();
		String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		String newFilename = timestamp + "_" + originalFilename;

		// Save file
		Path filePath = uploadPath.resolve(newFilename);
		file.transferTo(filePath.toFile());

		// Return relative path
		return "product/" + type + "/" + newFilename;

	}

	public Long count(){
		return productRepository.count();
	}

	private Product transferDtoToProduct(ProductDetailDto productDetailDto) {
		Product product = new Product(productDetailDto);
		product.setAuthor(authorService.findById(productDetailDto.getAuthorId()).orElse(null));
		product.setCategories(categoriesService.findById(productDetailDto.getCategoriesId()).orElse(null));
		product.setManufacturer(manufacturerService.findById(productDetailDto.getManufacturerId()).orElse(null));
		product.setPromotion(promotionService.findById(productDetailDto.getPromotionId()).orElse(null));
		return product;
	}

	public void addProductFormAttributes(Model model) {
		model.addAttribute("categories", categoriesService.findAll());
		model.addAttribute("manufacturer", manufacturerService.findAll());
		model.addAttribute("author", authorService.findAll());
		model.addAttribute("promotion", promotionService.findAll());
	}
}
