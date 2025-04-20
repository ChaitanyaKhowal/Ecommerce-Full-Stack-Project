package com.notifications.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.notifications.entities.OrderItems;

public interface OrderItemsRepositories extends JpaRepository<OrderItems, Integer>{

}
