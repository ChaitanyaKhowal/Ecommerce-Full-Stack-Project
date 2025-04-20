package com.product.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.dto.CategoryDto;
import com.product.dto.ProductDto;
import com.product.mapper.CategoryMapper;
import com.product.mapper.ProductMapper;
import com.product.repository.CategoryRepository;
import com.product.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository prodRepo;

	@Autowired
	private CategoryRepository categRepo;

	@Override
	public List<CategoryDto> findAllCategories() {

		// Older and Slower approach

		/*
		 * List<CategoryEntity> categories = categRepo.findAll(); List<CategoryDto>
		 * dtoList = new ArrayList<>();
		 * 
		 * for (CategoryEntity category : categories) { CategoryDto dto =
		 * CategoryMapper.convertToDto(category); dtoList.add(dto); } return dtoList;
		 */

		// Latest and Fastest approach
		return categRepo.findAll().stream().map(CategoryMapper::convertToDto).collect(Collectors.toList());

	}

	@Override
	public List<ProductDto> findProductsByCategoryId(Integer categoryId) {

		// Older and Slower approach

		/*
		 * List<ProductEntity> idList = prodRepo.findByCategoryCategoryId(categoryId);
		 * 
		 * if (!idList.isEmpty()) { List<ProductDto> prodDtoList = new ArrayList<>();
		 * 
		 * for (ProductEntity entity : idList) { ProductDto prodDto =
		 * ProductMapper.convertToDto(entity); prodDtoList.add(prodDto); } return
		 * prodDtoList; } return new ArrayList<>();
		 */

		// Latest and Fastest approach
		return prodRepo.findByCategoryCategoryId(categoryId).stream().map(ProductMapper::convertToDto)
				.collect(Collectors.toList());

	}

	@Override
	public ProductDto findByProductId(Integer productId) {

		// Older and Slower approach

		/*
		 * Optional<ProductEntity> byId = prodRepo.findById(productId);
		 * 
		 * if(byId.isPresent()) {
		 * 
		 * ProductEntity productEntity = byId.get(); return
		 * ProductMapper.convertToDto(productEntity);
		 * 
		 * }
		 * 
		 * return null;
		 */

		// Latest and Fastest approach
		return prodRepo.findById(productId).map(ProductMapper::convertToDto).orElse(null);

	}

	@Override
	public List<ProductDto> findByProductName(String productName) {

		// Older and Slower approach

		/*
		 * List<ProductEntity> byProductName = prodRepo.findByProductName(productName);
		 * 
		 * List<ProductDto> dtoList = new ArrayList<>();
		 * 
		 * if (!byProductName.isEmpty()) { for (ProductEntity entity : byProductName) {
		 * ProductDto prodDto = ProductMapper.convertToDto(entity);
		 * dtoList.add(prodDto); } } return dtoList;
		 */

		// Latest and Fastest approach
		return prodRepo.findByProductNameContaining(productName).stream().map(ProductMapper::convertToDto)
				.collect(Collectors.toList());
	}
}
