package com.christian.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="product") // Table name
public class Product {
    @Column(name="productId")
	@Id	
    private int productId;
    @Column(name="productName")
    private String productName;
    @Column(name="productStock")
    private int productStock;
    @Column(name="productImage")
    private String productImage;
    @Column(name="productPrice")
    private int productPrice;
    @Column(name="storageId")
    private String storageId;
}