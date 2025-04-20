package com.customer.mapper;

import org.modelmapper.ModelMapper;

import com.customer.dto.CustomerDto;
import com.customer.entities.CustomerEntity;

public class CustomerMapper {

	private static final ModelMapper mapper = new ModelMapper();

	public static CustomerDto convertToDto(CustomerEntity entity) {
		return mapper.map(entity, CustomerDto.class);
	}

	public static CustomerEntity convertToEntity(CustomerDto dto) {
		return mapper.map(dto, CustomerEntity.class);
	}

}
