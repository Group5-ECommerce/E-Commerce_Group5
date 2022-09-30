package com.hcl.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_rating") // Table name
public class ProductRating implements Serializable {

	@Id
	@ManyToOne(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "productId", unique = false)
	private Product product;
	
	@Id
	private String userId;
	
	private int rating;
}
