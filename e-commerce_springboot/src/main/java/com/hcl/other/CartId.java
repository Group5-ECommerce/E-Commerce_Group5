package com.hcl.other;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartId implements Serializable {

	private String oktaId;
	private int productId;
}
