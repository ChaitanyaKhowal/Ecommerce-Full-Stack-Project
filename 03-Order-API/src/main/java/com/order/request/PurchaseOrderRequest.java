package com.order.request;

import java.util.List;

import com.order.dto.AddressDto;
import com.order.dto.CustomerDto;
import com.order.dto.OrderDto;
import com.order.dto.OrderItemsDto;

import lombok.Data;

@Data
public class PurchaseOrderRequest {

	private CustomerDto customerDto;
	private AddressDto addressDto;
	private OrderDto orderDto;
	private List<OrderItemsDto> orderItemsDto;
	
}
