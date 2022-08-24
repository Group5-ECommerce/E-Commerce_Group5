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
@Table(name="address") // Table name
public class Address {
    @Column(name="addressId")
	@Id	
    private int addressId;
    @Column(name="userId")
    private int userId ;
    @Column(name="streetAddress")
    private String streetAddress;
    @Column(name="city")
    private String city;    
    @Column(name="state")
    private String state;
    @Column(name="zip")
    private int zip ;
    @Column(name="country")
    private String country;

    
}