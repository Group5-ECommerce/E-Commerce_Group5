package com.hcl.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="payment_info") // Table name
public class PaymentInfo {
    @Column(name="paymentId")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id	
    private int paymentId;
 
    @Column(name="cardHolderFirstName")
    private String cardHolderFirstName;
    @Column(name="cardHolderLastName")
    private String cardHolderLastName;
    
    @OneToOne
    @JoinColumn(name="orderId")
    private Order order;
    
    @Column(name="cardNumber")
    private String cardNumber;
    
    @OneToOne
    @JoinColumn(name = "billingAddress")
    private Address billingAddressId;
    
    @OneToOne
    @JoinColumn(name = "shippingAddress")
    private Address shippingAddressId;
}