package com.christian.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id	
    private int productId;
    @Column(name="productName")
    private String productName;
    @Column(name="productStock")
    private int productStock;
    @Column(name="productImage")
    private String productImage;
    @Column(name="productPrice", columnDefinition="Decimal(10,2)")
    private double productPrice;
    @Column(name="storageId")
    private String storageId;
    //store quantity to order, set up addresses?
    //Set up way for database to store roles if created in mySQL? Does writing to userRoles fix this?
}