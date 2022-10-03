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
@Table(name="order_address") // Table name
public class OrderAddress 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_addressId")
	private Integer addressId;

	@Column(name = "oktaId")
	private String oktaId;
	
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name="streetAddress")
    private String streetAddress;
    @Column(name="city")
    private String city;    
    @Column(name="state")
    private String state;
    @Column(name="zip")
    private String zip ;
    @Column(name="country")
    private String country;

}
