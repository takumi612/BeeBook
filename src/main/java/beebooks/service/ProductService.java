package beebooks.service;

import beebooks.dto.ProductDetailDto;
import beebooks.dto.ProductProjection;
import beebooks.entities.Images;
import beebooks.repository.AuthorRepository;
import beebooks.repository.CategoriesRepository;
import beebooks.repository.ImageRepository;
import beebooks.repository.ManufacturerRepository;
import beebooks.repository.PromotionRepository;
import beebooks.ultilities.Cart;
import beebooks.ultilities.CartItem;
import beebooks.ultilities.ResultUtil;
import beebooks.ultilities.searchUtil.ProductSearch;
import beebooks.entities.Product;
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
import org.springframework.web.bind.annotation.PathVariable;
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

	private final CategoriesRepository categoriesRepository;
	private final AuthorRepository authorRepository;
	private final ManufacturerRepository manufacturerRepository;
	private final PromotionRepository promotionRepository;
	private final ImageRepository imageRepository;
	private final ProductRepository productRepository;

	@Value("${app.upload.dir:${user.home}}")
	private String uploadDir;

	@Value("${app.upload.default}")
	private String defaultPath;

    public ProductService(CategoriesRepository categoriesRepository, AuthorRepository authorRepository, ManufacturerRepository manufacturerRepository, PromotionRepository promotionRepository, ImageRepository imageRepository, ProductRepository productRepository) {
        super(productRepository);
        this.categoriesRepository = categoriesRepository;
        this.authorRepository = authorRepository;
        this.manufacturerRepository = manufacturerRepository;
        this.promotionRepository = promotionRepository;
        this.imageRepository = imageRepository;
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
	public ResultUtil saveProduct(ProductDetailDto productDetailDto) throws IOException {

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
		if (productPictures != null) {
			if (productDetailDto.getRemovedImages() != null) {
				for (String path : productDetailDto.getRemovedImages()) {
					deleteFile(path);
					imageRepository.deleteProductImageByPath(path);
				}
			}
			// Save new product pictures
			savePictures(product, productPictures);
		}else if(productDetailDto.getImages()!=null && !productDetailDto.getImages().isEmpty()){
			product.setImages(productDetailDto.getImages());
		}
		product.setSeo(new Slugify().slugify(product.getTitle()));
		return productRepository.save(product);
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
	@Transactional
    public void savePictures(Product product, MultipartFile[] productPictures){
		Arrays.stream(productPictures)
				.filter(pic -> pic != null && !pic.isEmpty())
				.forEach(pic -> {
					try{
						String picturePath = saveFile(pic, "pictures");
						Images pi = new Images();
						pi.setPath(picturePath);
						pi.setTitle(pic.getOriginalFilename());
						pi.setProduct(product);
						imageRepository.save(pi);
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

	public void removeProductFromCart(final HttpServletRequest request,
									  @PathVariable("productId") int productId) {
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		List<CartItem> cartItem = cart.getCartItems();
		cartItem.removeIf(item->item.getProductId()==productId);
		session.setAttribute("cart", cart);
		session.setAttribute("totalItems", cartItem.size());
	}

	public ResponseEntity<Map<String, Object>> addItemToCart(final HttpServletRequest request,
															 final CartItem cartItem) {
		Map<String, Object> jsonResult = new HashMap<>();
		// Kiểm tra số lượng tồn kho của sản phẩm
		Product productInDb = findProductById(cartItem.getProductId());
		cartItem.setProduct(productInDb);

		log.info("-------------json1 :{}", jsonResult);
		HttpSession session = request.getSession();
		Cart cart;
		if (session.getAttribute("cart") != null) {
			cart = (Cart) session.getAttribute("cart");
		} else {
			cart = new Cart();
		}

		List<CartItem> cartItems = cart.getCartItems();
		// Kiểm tra trong Cart đã có sản phẩm chua
		boolean isExists = false;
		for (CartItem item : cartItems) {
			if (item.getProductId() == cartItem.getProductId()) {
				isExists = true;
				item.setQuantity(item.getQuantity() + cartItem.getQuantity());
			}
		}

		if (!isExists) {
			cartItem.setProductName(productInDb.getTitle());
			if (productInDb.isPromotionValid()){
				cartItem.setPriceUnit(productInDb.getPrice() - productInDb.getPrice()*(productInDb.getPromotion().getPercent()/100));
			} else {
				cartItem.setPriceUnit(productInDb.getPrice());
			}
			cart.getCartItems().add(cartItem);
		}

		jsonResult.put("status", "TC");
		jsonResult.put("totalItems", cart.getCartItems().size());
		session.setAttribute("cart", cart);
		session.setAttribute("totalItems", cart.getCartItems().size());
		return ResponseEntity.ok(jsonResult);
	}

	public Long count(){
		return productRepository.count();
	}

	private Product transferDtoToProduct(ProductDetailDto productDetailDto) {
		Product product = new Product(productDetailDto);
		product.setAuthor(authorRepository.findById(productDetailDto.getAuthorId()).orElse(null));
		product.setCategories(categoriesRepository.findById(productDetailDto.getCategoriesId()).orElse(null));
		product.setManufacturer(manufacturerRepository.findById(productDetailDto.getManufacturerId()).orElse(null));
		product.setPromotion(promotionRepository.findById(productDetailDto.getPromotionId()).orElse(null));
		return product;
	}

	public void addProductFormAttributes(Model model) {
		model.addAttribute("categories", categoriesRepository.findAll());
		model.addAttribute("manufacturer", manufacturerRepository.findAll());
		model.addAttribute("author", authorRepository.findAll());
		model.addAttribute("promotion", promotionRepository.findAll());
	}
}
