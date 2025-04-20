package com.customer.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CustomerDto {

	private Integer customerId;
	private String customerName;
	private String customerEmail;
	private Long customerPhone;
	private String password;
	private String passwordUpdated;
	private LocalDate createdAt;
	private LocalDate lastUpdated;

}
