package com.hcl.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hcl.entity.OrderItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    @ToString.Exclude
    private User user;
    
    @Column(name="trackingNumber")
    private String trackingNumber; 
    
    @Column(name="totalPrice", columnDefinition="Decimal(10,2)")
    private double totalPrice;
    
    @OneToOne
    @JoinColumn(name="shippingAddressId")
    private Address shippingAddress;
    
    @Column(name="orderTime")
    private Timestamp orderTime;
    @Column(name="orderStatus")
    private String orderStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    // Thanks to https://stackoverflow.com/a/61870411 for this property.
    // Marking CascadeType as all in the OneToMany annotation wasn't adding a property, and I wasn't sure why. It seems to be a Hibernate <-> JPA nuance.
    @OnDelete(action = OnDeleteAction.CASCADE) 
    private List<OrderItem> items = new ArrayList<OrderItem>();
}