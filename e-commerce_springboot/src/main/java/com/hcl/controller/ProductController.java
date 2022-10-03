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
	public Product addProduct(@RequestBody Product product) {
		return productRepo.save(product);
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
	public Product updateProduct(@RequestBody Product newProduct) {
		return productRepo.save(newProduct);
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
		Optional<ProductRating> op_prevRating = ratingRepo.findById(prId);
		
		// If a rating for this product by this user already exists (the user has previously rated it), update that rating.
		if (op_prevRating.isPresent()) {			
			ProductRating prevRating = op_prevRating.get();
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
