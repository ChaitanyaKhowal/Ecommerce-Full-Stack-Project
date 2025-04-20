package com.product.mapper;

import org.modelmapper.ModelMapper;

import com.product.dto.ProductDto;
import com.product.entities.ProductEntity;

public class ProductMapper {

	public static final ModelMapper mapper = new ModelMapper();

	public static ProductDto convertToDto(ProductEntity entity) {
		return mapper.map(entity, ProductDto.class);
	}

	public static ProductEntity convertToEntity(ProductDto dto) {
		return mapper.map(dto, ProductEntity.class);
	}

}
