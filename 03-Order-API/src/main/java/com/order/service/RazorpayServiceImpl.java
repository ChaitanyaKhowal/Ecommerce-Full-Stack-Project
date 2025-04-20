package com.order.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;

import lombok.SneakyThrows;

@Service
public class RazorpayServiceImpl implements RazorpayService {

	private RazorpayClient razorpayClient;

	@SneakyThrows
	public RazorpayServiceImpl(@Value("${razorpay.id}") String keyId, @Value("${razorpay.secret}") String keySecret) {
		System.out.println("Razorpay ID: " + keyId);
		System.out.println("Razorpay Secret: " + keySecret);
		this.razorpayClient = new RazorpayClient(keyId, keySecret);
	}

	@Override
	@SneakyThrows
	public Order createPaymentOrder(double amount) {

		JSONObject orderRequest = new JSONObject();
		orderRequest.put("amount", amount * 100);
		orderRequest.put("currency", "INR");
		orderRequest.put("payment_capture", 1);

		return razorpayClient.orders.create(orderRequest);
	}

}
