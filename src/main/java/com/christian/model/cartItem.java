package com.christian.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.christian.entity.Order;
import com.christian.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
public final class cartItem implements Serializable{
	@ManyToOne
	@Id
    @JoinColumn(name = "productId")
	private Product productId;
	
	@Id
	private int amt;
}
