package com.hcl.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.hcl.other.CartId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(CartId.class)
public class Cart implements Serializable {

	@Id
	private String oktaId;
	@Id
	private int productId;

	private String productName;
	private String productImage;
	private int productStock;
	private float productPrice;
	private int storageId;
}
