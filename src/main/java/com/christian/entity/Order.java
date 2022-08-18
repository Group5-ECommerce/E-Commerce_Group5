package com.christian.entity;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.christian.model.cartItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="orders") // Table name
public class Order {
    @Column(name="orderId")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id	
    private int orderId;
    
    // @ManyToOne
    // @JoinColumn(name = "userId")
    private int userId;
    
    @Column(name="trackingNumber")
    private String trackingNumber;
    @Column(name="totalPrice")
    private double totalPrice;
    @Column(name="shippingAddressId")
    private int shippingAddressId;
    @Column(name="orderTime")
    private Timestamp orderTime;
    @Column(name="orderStatus")
    private String orderStatus;

    // @OneToMany
    // @JoinColumn(name = "itemId")
    // private List<cartItem> items;
}