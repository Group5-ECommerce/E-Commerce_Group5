package com.christian.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor //review page to see how to map
@Entity
@Table(name="order_item") // Table name
public class OrderItem implements Serializable{
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId") 
	private Order order;
	
	@Id
	@ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "productId", unique = false) 
	private Product product;
	
	private int amt;
}
