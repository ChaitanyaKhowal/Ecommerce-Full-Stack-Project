package com.customer.service;

import com.customer.dto.CustomerDto;
import com.customer.dto.ResetPwdDto;
import com.customer.response.AuthResponse;

public interface CustomerService {

	public Boolean register(CustomerDto customDto);

	public Boolean isEmailUnique(String emailId);

	public Boolean resetPassword(ResetPwdDto pwdDto);

	public CustomerDto getCustomerByEmail(String emailId);
	
	public Boolean forgetPassword(String emailId);

	public AuthResponse login(CustomerDto customerDto);

}
