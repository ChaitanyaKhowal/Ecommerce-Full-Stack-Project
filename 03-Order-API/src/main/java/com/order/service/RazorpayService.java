package com.order.service;

import com.razorpay.Order;

public interface RazorpayService {

	public Order createPaymentOrder(double amount);	
	
}
