package com.hcl.dto;

import java.util.List;

import com.hcl.entity.PaymentInfo;
import com.hcl.model.cartItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class Purchase 
{
	private PaymentInfo payment;
	private List<cartItem> items;
	private String message;

}
