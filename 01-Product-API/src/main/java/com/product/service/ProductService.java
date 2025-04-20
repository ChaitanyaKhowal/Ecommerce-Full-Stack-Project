package com.product.service;

import java.util.List;

import com.product.dto.CategoryDto;
import com.product.dto.ProductDto;

public interface ProductService {

	 //To get all the categories
	public List<CategoryDto> findAllCategories();
	
	//To get Products based on the Category Id
	public List<ProductDto> findProductsByCategoryId(Integer categoryId); 
	
	//To get Product based on Product Id
	public ProductDto findByProductId(Integer productId);
	
	//To get Product by Product Name
	public List<ProductDto> findByProductName(String productName);
	
}
