package com.hcl.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @Column(name="category")
    private String category;
    @Column(name="numberOfRatings", columnDefinition = "integer default 0")
    private int numberOfRatings = 0;
    @Column(name="totalOfRatings" , columnDefinition = "integer default 0")
    private int totalOfRatings = 0;
    
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	// Thanks to https://stackoverflow.com/a/61870411 for this property.
	// Marking CascadeType as all in the OneToMany annotation wasn't adding a
	// property, and I wasn't sure why. It seems to be a Hibernate <-> JPA nuance.
	@OnDelete(action = OnDeleteAction.CASCADE)
    List<ProductRating> ratings;
	
	public void addRating(ProductRating rating) {
		ratings.add(rating);
	}
}