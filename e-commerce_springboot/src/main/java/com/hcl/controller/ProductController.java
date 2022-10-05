package com.hcl.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.dto.ProductDTO;
import com.hcl.entity.Product;
import com.hcl.entity.ProductRating;
import com.hcl.model.ProductRatingId;
import com.hcl.repo.ProductRatingRepository;
import com.hcl.repo.ProductRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "Products")
public class ProductController {
	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private ProductRatingRepository ratingRepo;

	@PostMapping("/product")
	@ApiOperation(value = "Add Product")
	@PreAuthorize("hasAuthority('Admin')")
	public Product addProduct(@RequestBody ProductDTO pDTO) {
		Product p = new Product();
		p = copyDtoToProd(p, pDTO);
		return productRepo.save(p);
	}

	private Product copyDtoToProd(Product p, ProductDTO pDTO) {
		p.setCategory(pDTO.getCategory());
		p.setNumberOfRatings(pDTO.getNumberOfRatings());
		p.setProductId(pDTO.getProductId());
		p.setProductImage(pDTO.getProductImage());
		p.setProductName(pDTO.getProductName());
		p.setProductPrice(pDTO.getProductPrice());
		p.setProductStock(pDTO.getProductStock());
		p.setStorageId(pDTO.getStorageId());
		p.setTotalOfRatings(pDTO.getTotalOfRatings());
		return p;
	}

	@GetMapping("/product")
	@ApiOperation(value = "Show All Products")
	public List<Product> listAllProduct() {
		return productRepo.findAll();
	}

	@GetMapping("/product/{id}")
	@ApiOperation(value = "Get Product With Id")
	public Optional<Product> getProductId(@PathVariable Integer id) {
		return productRepo.findById(id);
	}

	@PutMapping("/product")
	@ApiOperation(value = "Update specific product")
	@PreAuthorize("hasAuthority('Admin')")
	public Product updateProduct(@RequestBody ProductDTO pDTO) {
		Optional<Product> opPrevRating = productRepo.findById(pDTO.getProductId());
		if (!opPrevRating.isPresent()) return null;
		Product p = opPrevRating.get();
		
		p = copyDtoToProd(p, pDTO);
		
		return productRepo.save(p);
	}

	@DeleteMapping("/product/{id}")
	@ApiOperation(value = "Delete Product With Id")
	@PreAuthorize("hasAuthority('Admin')")
	public void deleteProduct(@PathVariable Integer id) {
		productRepo.deleteById(id);
	}

	@PostMapping("/rateProduct/{productId}")
	@PreAuthorize("hasAuthority('Customer') && !hasAuthority('Admin')")
	public void rateProduct(Principal principal, @PathVariable Integer productId, @RequestParam Integer rating) {

		Optional<Product> op_product = productRepo.findById(productId);

		if (!op_product.isPresent())
			return;

		Product product = op_product.get();
		String userId = principal.getName();

		ProductRatingId prId = new ProductRatingId(product, userId);
		Optional<ProductRating> opPrevRating = ratingRepo.findById(prId);
		
		// If a rating for this product by this user already exists (the user has previously rated it), update that rating.
		if (opPrevRating.isPresent()) {			
			ProductRating prevRating = opPrevRating.get();
			int totalOfRatings = product.getTotalOfRatings() - prevRating.getRating() + rating;
			product.setTotalOfRatings(totalOfRatings);
			prevRating.setRating(rating);
		}
		// If the user has not rated the product yet, create a new rating and update the product's total ratings.
		else {
			ProductRating pr = new ProductRating(product, userId, rating);
			int totalOfRatings = product.getTotalOfRatings() + rating;
			product.setTotalOfRatings(totalOfRatings);
			int numberOfRatings = product.getNumberOfRatings() + 1;
			product.setNumberOfRatings(numberOfRatings);
			product.addRating(pr);
		}

		productRepo.save(product);
	}

}
