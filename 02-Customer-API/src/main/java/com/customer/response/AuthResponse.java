package com.customer.response;

import com.customer.dto.CustomerDto;

import lombok.Data;

@Data
public class AuthResponse {

	private CustomerDto customer;

	private String token;

}
