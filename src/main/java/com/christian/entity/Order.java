package com.christian.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import com.christian.entity.OrderItem;

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
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Id	
    private int orderId;
    
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
    
    @Column(name="trackingNumber")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int trackingNumber; //should I make this a UUID?
    
    @Column(name="totalPrice")
    private double totalPrice;
    @Column(name="shippingAddressId")
    private int shippingAddressId;
    @Column(name="orderTime")
    private Timestamp orderTime;
    @Column(name="orderStatus")
    private String orderStatus;

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true
        )
    private List<OrderItem> items = new ArrayList<OrderItem>();
}