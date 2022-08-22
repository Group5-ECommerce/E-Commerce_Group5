package com.christian.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor //review page to see how to map
@Entity
public class OrderItem implements Serializable{

	@ManyToOne(fetch = FetchType.LAZY)
	@Id
    @JoinColumn(name = "orderId") 
	private Order order;
	
	@OneToOne
	@Id
    @JoinColumn(name = "productId") 
	private Product product;
	
	@Id
	private int amt;
}
