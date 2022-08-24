package com.christian.model;

import com.christian.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class cartItem{
	private Product product;
	private int amt;
}
