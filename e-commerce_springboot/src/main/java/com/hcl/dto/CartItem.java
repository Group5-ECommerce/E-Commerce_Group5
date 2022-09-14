package com.hcl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

	private String userId;
	private int productId;
	private String productName;
	private String productImage;
	private int productStock;
	private float productPrice;
	private int storageId;
}
