package com.order.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.order.entities.AddressEntity;

public interface AddressRepository extends JpaRepository<AddressEntity, Integer>{

}
