package com.hcl.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hcl.model.ProductRatingId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(ProductRatingId.class)
@Table(name = "product_rating") // Table name
public class ProductRating implements Serializable {

	@Id
	@ManyToOne
	@JoinColumn(name = "productId", unique = false)
	private Product product;
	
	@Id
	@Column(name = "oktaId")
	private String oktaId;
	
	private int rating;
}

