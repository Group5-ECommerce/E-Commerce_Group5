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
import lombok.ToString;

@Data
@AllArgsConstructor // review page to see how to map
@NoArgsConstructor
@Entity
@Table(name = "order_item") // Table name
public class OrderItem implements Serializable {

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orderId")
	@JsonIgnore
	@ToString.Exclude // Thanks to https://stackoverflow.com/a/56263668 for mentioning this
						// annotation.
	private Order order;

	@Id
	@ManyToOne(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "productId", unique = false)
	private Product product;

	private int amt;
}
