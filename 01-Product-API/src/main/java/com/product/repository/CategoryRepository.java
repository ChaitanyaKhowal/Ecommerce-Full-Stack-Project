package com.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.product.entities.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

}
