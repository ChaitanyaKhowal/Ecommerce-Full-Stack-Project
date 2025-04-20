package com.notifications.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.notifications.entities.AddressEntity;

public interface AddressRepository extends JpaRepository<AddressEntity, Integer>{

}
