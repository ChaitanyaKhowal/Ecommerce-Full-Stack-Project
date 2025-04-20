package com.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.product.entities.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

	public List<ProductEntity> findByCategoryCategoryId(Integer categoryId);
	
	public List<ProductEntity> findByProductNameContaining(String  name);
	
}
