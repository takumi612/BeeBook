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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Slf4j
@Service
@Transactional
public class ProductService extends BaseService<Product,Integer> {

	private final CategoriesRepository categoriesRepository;
	private final AuthorRepository authorRepository;
	private final ManufacturerRepository manufacturerRepository;
	private final PromotionRepository promotionRepository;
	private final ImageRepository imageRepository;
	private final ProductRepository productRepository;
	private final FileService fileService;

	@Value("${app.upload.default}")
	private String defaultPath;

	public ProductService(CategoriesRepository categoriesRepository,
						AuthorRepository authorRepository,
						ManufacturerRepository manufacturerRepository,
						PromotionRepository promotionRepository,
						ImageRepository imageRepository,
						ProductRepository productRepository,
						FileService fileService) {
		super(productRepository);
		this.categoriesRepository = categoriesRepository;
		this.authorRepository = authorRepository;
		this.manufacturerRepository = manufacturerRepository;
		this.promotionRepository = promotionRepository;
		this.imageRepository = imageRepository;
		this.productRepository = productRepository;
		this.fileService = fileService;
	}


	public Product findProductById(int id) {
		return productRepository.findById(id).orElse(null);
	}

	public Page<ProductProjection> findProductByTitle(ProductSearch search) {
		return productRepository.findByTitleLikeIgnoreCase(search.getKeyword(), search.getPage());
	}
	public List<ProductProjection> findProductByTitle(String title) {
		return productRepository.findByTitleLikeIgnoreCase(title);
	}

	public Page<ProductDetailDto> search(ProductSearch searchModel) {
		Specification<Product> spec = ProductSpecification.getProductSpecification(searchModel);
		return productRepository.findAll(spec, searchModel.getPage())
				.map(ProductDetailDto::new);
	}

	public Page<ProductProjection> findAllToDto(Pageable pageable){
		return productRepository.findAllProjectedByStatus(true,pageable);
	}

	public Page<ProductProjection> findByCategories(ProductSearch searchModel){
		return productRepository.findAllProjectedByCategoriesIdAndStatus(
				searchModel.getCategoryId(), 
				true, 
				searchModel.getPage()
		);
	}

	@Transactional
	public ResultUtil saveProduct(ProductDetailDto productDetailDto) throws IOException {
		if (isNewProductWithExistingTitle(productDetailDto)) {
			return new ResultUtil("errorMessage", "Sách đã có trong CSDL");
		}
		save(productDetailDto);
		return new ResultUtil("successMessage", "Thành công lưu sách");
	}

	private boolean isNewProductWithExistingTitle(ProductDetailDto productDetailDto) {
		return productDetailDto.getId() == null && 
			   !findProductByTitle(productDetailDto.getTitle()).isEmpty();
	}

	@Transactional
	public Product save(ProductDetailDto productDetailDto) throws IOException {
		Product product = transferDtoToProduct(productDetailDto);
		handleProductAvatar(product, productDetailDto);
		handleProductImages(product, productDetailDto);
		product.setSeo(new Slugify().slugify(product.getTitle()));
		return productRepository.save(product);
	}

	private void handleProductAvatar(Product product, ProductDetailDto dto) throws IOException {
		MultipartFile avatar = dto.getProductAvatar();
		if (avatar != null && !avatar.isEmpty()) {
			if (dto.getAvatar() != null && !dto.getAvatar().isEmpty()) {
				fileService.deleteFile(dto.getAvatar());
			}
			String avatarPath = fileService.saveFile(avatar, "avatar");
			product.setAvatar(avatarPath);
		} else {
			product.setAvatar(dto.getAvatar() != null && !dto.getAvatar().isEmpty() 
					? dto.getAvatar() 
					: defaultPath);
		}
	}

	private void handleProductImages(Product product, ProductDetailDto dto) throws IOException {
		MultipartFile[] productPictures = dto.getProductPictures();
		if (productPictures != null) {
			handleRemovedImages(dto.getRemovedImages());
			saveNewProductImages(product, productPictures);
		} else if (dto.getImages() != null && !dto.getImages().isEmpty()) {
			product.setImages(dto.getImages());
		}
	}

	private void handleRemovedImages(List<String> removedImages) {
		if (removedImages != null) {
			for (String path : removedImages) {
				fileService.deleteFile(path);
				imageRepository.deleteProductImageByPath(path);
			}
		}
	}

	private void saveNewProductImages(Product product, MultipartFile[] pictures) {
		Arrays.stream(pictures)
				.filter(pic -> pic != null && !pic.isEmpty())
				.forEach(pic -> {
					try {
						String picturePath = fileService.saveFile(pic, "pictures");
						Images image = createProductImage(pic, picturePath, product);
						imageRepository.save(image);
						product.addProductImage(image);
					} catch (IOException e) {
						log.error("Error saving product picture", e);
					}
				});
	}

	private Images createProductImage(MultipartFile file, String path, Product product) {
		Images image = new Images();
		image.setPath(path);
		image.setTitle(file.getOriginalFilename());
		image.setProduct(product);
		return image;
	}

	@Transactional
	public Boolean deleteProduct(int id) {
		return productRepository.findById(id)
				.map(product -> {
					product.setStatus(false);
					productRepository.save(product);
					return true;
				})
				.orElse(false);
	}

	public ResponseEntity<Map<String, Object>> updateQuantityCart(
			HttpServletRequest request, 
			CartItem cartItem, 
			String action) {
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		Map<String, Object> result = updateCartItemQuantity(cart, cartItem, action);
		session.setAttribute("cart", cart);
		return ResponseEntity.ok(result);
	}

	private Map<String, Object> updateCartItemQuantity(Cart cart, CartItem cartItem, String action) {
		Map<String, Object> result = new HashMap<>();
		cart.getCartItems().stream()
				.filter(item -> item.getProductId() == cartItem.getProductId())
				.findFirst()
				.ifPresent(item -> {
					Product product = findProductById(item.getProductId());
					updateItemQuantity(item, product, action, result);
					result.put("status", "TC");
					result.put("itemPrice", item.getPriceUnit() * item.getQuantity());
					result.put("totalPrice", cart.getTotalPrice());
					result.put("currentProductQuantity", item.getQuantity());
				});
		return result;
	}

	private void updateItemQuantity(CartItem item, Product product, String action, Map<String, Object> result) {
		if ("add".equals(action)) {
			if (item.getQuantity() < product.getQuantity()) {
				item.setQuantity(item.getQuantity() + 1);
			} else {
				result.put("error", "Vật phẩm không đủ số lượng");
			}
		} else if ("minus".equals(action) && item.getQuantity() > 1) {
			item.setQuantity(item.getQuantity() - 1);
		}
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
