package com.customer.dto;

import lombok.Data;

@Data
public class ResetPwdDto {

	private String customerEmail;
	private String currentPassword;
	private String newPassword;
	private String confirmPassword;
	
}
