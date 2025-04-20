package com.order.service;

import java.util.List;

import com.order.dto.OrderDto;
import com.order.dto.PaymentCallBackDto;
import com.order.request.PurchaseOrderRequest;
import com.order.response.PurchaseOrderResponse;

public interface OrderService {

	public PurchaseOrderResponse createOrder(PurchaseOrderRequest orderReq);

	public PurchaseOrderResponse updateOrder(PaymentCallBackDto paymentCallBackDto);

	public List<OrderDto> getOrdersByEmail(String email);

}
