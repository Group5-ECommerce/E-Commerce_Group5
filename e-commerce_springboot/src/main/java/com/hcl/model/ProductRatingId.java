package com.hcl.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hcl.entity.Product;
import com.hcl.entity.ProductRating;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductRatingId implements Serializable{
    public ProductRatingId(Product product, String oktaId) {
		this.product = product;
		this.oktaId = oktaId;
	}

	private Product product;

    private String oktaId;
}
