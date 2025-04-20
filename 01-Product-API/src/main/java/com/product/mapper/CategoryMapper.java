package com.product.mapper;

import org.modelmapper.ModelMapper;

import com.product.dto.CategoryDto;
import com.product.entities.CategoryEntity;

public class CategoryMapper {

	public static final ModelMapper mapper = new ModelMapper();

	public static CategoryDto convertToDto(CategoryEntity entity) {
		return mapper.map(entity, CategoryDto.class);
	}

	public static CategoryEntity convertToEntity(CategoryDto dto) {
		return mapper.map(dto, CategoryEntity.class);
	}

}
