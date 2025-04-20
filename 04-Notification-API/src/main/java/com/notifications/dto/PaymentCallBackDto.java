package com.notifications.dto;

import lombok.Data;

@Data
public class PaymentCallBackDto {

	private String razorPayOrderId;
	private String razorPayPaymentId;
	private String razorPaymentSignature;
	
}
