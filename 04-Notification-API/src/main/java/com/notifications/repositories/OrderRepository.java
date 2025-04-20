package com.notifications.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.notifications.entities.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {

	public OrderEntity findByRazorPayOrderId(String razorPayOrderId);

	public List<OrderEntity> findByEmail(String email);

	public List<OrderEntity> findByDeliveryDate(LocalDate deliveryDate);

}
