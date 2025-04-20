package com.order.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.order.entities.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer>{

	public OrderEntity findByRazorPayOrderId(String razorPayOrderId);
	
	public List<OrderEntity> findByEmail(String email);
	
}
