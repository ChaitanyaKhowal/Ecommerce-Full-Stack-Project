package com.order.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class OrderDto {

	private String orderTrackingNum;
	private String razorPayOrderId;
	private String email;
	private String orderStatus;
	private Double totalPrice;
	private Integer totalQuantity;
	private String razorPayPaymentId;
	private String invoiceUrl;
	private LocalDate deliveryDate;
	
}
