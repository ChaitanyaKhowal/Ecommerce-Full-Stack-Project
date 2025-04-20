package com.order.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.order.dto.OrderDto;
import com.order.dto.PaymentCallBackDto;
import com.order.request.PurchaseOrderRequest;
import com.order.response.ApiResponse;
import com.order.response.PurchaseOrderResponse;
import com.order.service.OrderService;

@RestController
@CrossOrigin
public class OrderRestController {

	@Autowired
	private OrderService orderService;

	@PostMapping("/order")
	private ResponseEntity<ApiResponse<PurchaseOrderResponse>> createOrder(@RequestBody PurchaseOrderRequest request) {

		ApiResponse<PurchaseOrderResponse> response = new ApiResponse<>();

		PurchaseOrderResponse orderResp = orderService.createOrder(request);

		if (orderResp != null) {
			response.setStatus(200);
			response.setMessage("Order Created");
			response.setData(orderResp);

			return new ResponseEntity<ApiResponse<PurchaseOrderResponse>>(response, HttpStatus.OK);

		} else {

			response.setStatus(500);
			response.setMessage("Order Creation failed");
			response.setData(null);

			return new ResponseEntity<ApiResponse<PurchaseOrderResponse>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@PutMapping("/order")
	public ResponseEntity<ApiResponse<PurchaseOrderResponse>> updateOrder(
			@RequestBody PaymentCallBackDto paymentCallBackDto) {

		ApiResponse<PurchaseOrderResponse> response = new ApiResponse<>();

		PurchaseOrderResponse orderResp = orderService.updateOrder(paymentCallBackDto);

		if (orderResp != null) {
			response.setStatus(200);
			response.setMessage("Order Updated Successfully");
			response.setData(orderResp);

			return new ResponseEntity<ApiResponse<PurchaseOrderResponse>>(response, HttpStatus.OK);

		} else {

			response.setStatus(500);
			response.setMessage("Order Updation Failed");
			response.setData(null);

			return new ResponseEntity<ApiResponse<PurchaseOrderResponse>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@GetMapping("/orders/{email}")
	public ResponseEntity<ApiResponse<List<OrderDto>>> getOrder(@PathVariable String email) {

		ApiResponse<List<OrderDto>> response = new ApiResponse<>();

		List<OrderDto> ordersByEmail = orderService.getOrdersByEmail(email);

		if (!ordersByEmail.isEmpty()) {
			response.setStatus(200);
			response.setMessage("Fetched Orders");
			response.setData(ordersByEmail);

			return new ResponseEntity<ApiResponse<List<OrderDto>>>(response, HttpStatus.OK);

		} else {

			response.setStatus(500);
			response.setMessage("Failed to fetched orders");
			response.setData(null);

			return new ResponseEntity<ApiResponse<List<OrderDto>>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
