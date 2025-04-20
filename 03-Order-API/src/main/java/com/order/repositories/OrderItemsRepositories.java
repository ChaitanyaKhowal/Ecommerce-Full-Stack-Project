package com.order.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.order.entities.OrderItems;

public interface OrderItemsRepositories extends JpaRepository<OrderItems, Integer>{

}
