package com.order.dto;

import lombok.Data;

@Data
public class OrderItemsDto {

	private Integer orderId;
	private String orderName;
	private Integer quantity;
	private Double unitPrice;
	private String imageUrl;

}
