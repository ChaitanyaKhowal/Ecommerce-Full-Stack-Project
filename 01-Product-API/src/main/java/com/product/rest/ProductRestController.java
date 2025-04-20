package com.product.rest;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.product.dto.CategoryDto;
import com.product.dto.ProductDto;
import com.product.response.ApiResponse;
import com.product.service.ProductService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ProductRestController {

	@Autowired
	private ProductService service;

	@GetMapping("/categories")
	public ResponseEntity<ApiResponse<List<CategoryDto>>> getAllCategories() {

		List<CategoryDto> allCategories = service.findAllCategories();

		ApiResponse<List<CategoryDto>> response = new ApiResponse<>();

		if (!allCategories.isEmpty()) {
			response.setStatus(200);
			response.setMessage("Fetched Categories successfully");
			response.setData(allCategories);

			return new ResponseEntity<>(response, HttpStatus.OK);

		} else {

			response.setStatus(500);
			response.setMessage("Failed to fetched categories");
			response.setData(null);

			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@GetMapping("/products/{categoryId}")
	public ResponseEntity<ApiResponse<List<ProductDto>>> getAllProducts(@PathVariable Integer categoryId) {

		List<ProductDto> products = service.findProductsByCategoryId(categoryId);

		ApiResponse<List<ProductDto>> response = new ApiResponse<>();

		if (products.isEmpty()) {
			response.setStatus(200);
			response.setMessage("No products available");
			response.setData(Collections.emptyList());

			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.setStatus(200);
			response.setMessage("Fetched products successfully");
			response.setData(products);

			return new ResponseEntity<>(response, HttpStatus.OK);

		}

	}

	@GetMapping("/product/{productId}")
	public ResponseEntity<ApiResponse<ProductDto>> getProductById(@PathVariable Integer productId) {

		ProductDto byProductId = service.findByProductId(productId);

		ApiResponse<ProductDto> response = new ApiResponse<>();

		if (byProductId != null) {
			response.setStatus(200);
			response.setMessage("Product fetched successfully");
			response.setData(byProductId);

			return new ResponseEntity<>(response, HttpStatus.OK);

		} else {

			response.setStatus(500);
			response.setMessage("Failed to fetched products");
			response.setData(null);

			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@GetMapping("/productByName/{productName}")
	public ResponseEntity<ApiResponse<List<ProductDto>>> getProductByName(@PathVariable String productName) {

		List<ProductDto> byProductName = service.findByProductName(productName);

		ApiResponse<List<ProductDto>> response = new ApiResponse<>();

		if (!byProductName.isEmpty()) {
			response.setStatus(200);
			response.setMessage("Product fetched successfully");
			response.setData(byProductName);

			return new ResponseEntity<>(response, HttpStatus.OK);

		} else {

			response.setStatus(500);
			response.setMessage("Failed to fetched products");
			response.setData(null);

			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

}
