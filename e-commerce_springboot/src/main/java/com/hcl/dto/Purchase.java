package com.hcl.dto;

import com.hcl.entity.PaymentInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class Purchase 
{
	private PaymentInfo payment;
	private String message;

}
