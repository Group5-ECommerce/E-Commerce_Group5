package com.hcl.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
@Table(name="payment_info") // Table name
public class PaymentInfo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="paymentId")
    private int paymentId;
 
    @OneToOne
    @JoinColumn(name="orderId")
    private Order order;
    
    @OneToOne
    @JoinColumn(name = "billingAddress")
    private OrderAddress billingAddressId;
    
    @OneToOne
    @JoinColumn(name = "shippingAddress")
    private OrderAddress shippingAddressId;
}