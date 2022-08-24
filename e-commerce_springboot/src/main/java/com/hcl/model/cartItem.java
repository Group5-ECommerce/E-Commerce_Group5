package com.hcl.model;

import com.hcl.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class cartItem{
	private Product product;
	private int amt;
}
