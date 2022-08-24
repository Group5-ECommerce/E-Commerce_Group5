package com.hcl.entity;

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
@Table(name="payment_info") // Table name
public class PaymentInfo {
    @Column(name="paymentId")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id	
    private int paymentId;
    @Column(name="billingAddressId")
    private int billingAddressId;
    @Column(name="orderId")
    private int orderId;
    @Column(name="cardNumber")
    private int cardNumber;
}