package com.hcl.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.dto.CartItem;
import com.hcl.entity.Cart;
import com.hcl.repo.CartRepository;

@RestController
//@CrossOrigin(origins = "https://ecommerce-capstone-frontend.azurewebsites.net")
public class CartController {
	@Autowired
	private CartRepository cartRepository;

	@GetMapping("/cart")
	public List<Cart> getCart(Principal principal) {
		List<Cart> items = new ArrayList();
		String oktaId = principal.getName();
		if (oktaId != null) {
			items = cartRepository.findByOktaId(oktaId);
			System.out.println("on sign in oktaId: " + oktaId);
		}

		System.out.println("items: " + items);
		return items;
	}

	@PutMapping("/cart")
	public void updateCart(@RequestBody List<CartItem> userCart, Principal principal) {
//		System.out.println(userCart);
		String oktaId = principal.getName();
		// delete existing
		cartRepository.deleteAllByOktaId(oktaId); // entries

		// replace with new for each

		userCart.forEach((item) -> {
			Cart cartItem = new Cart();
			cartItem.setOktaId(item.getUserId());
			cartItem.setProductId(item.getProductId());
			cartItem.setProductImage(item.getProductImage());
			cartItem.setProductName(item.getProductName());
			cartItem.setProductPrice(item.getProductPrice());
			cartItem.setProductStock(item.getProductStock());
			cartItem.setStorageId(item.getStorageId());
			cartItem.setAmt(item.getAmt());
			cartRepository.save(cartItem);
		});
	}
}
