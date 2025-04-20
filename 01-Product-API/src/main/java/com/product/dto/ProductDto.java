package com.product.dto;

import java.math.BigDecimal;
import java.sql.Date;

import lombok.Data;

@Data
public class ProductDto {

	private Integer productId;
	private String productName;
	private String productDesc;
	private String productTitle;
	private BigDecimal unitPrice;
	private String imageUrl;
	private boolean active;
	private Integer unitsStock;
	private Integer categoryId;
	private Date dateCreated;
	private Date lastUpdated;
	
}
