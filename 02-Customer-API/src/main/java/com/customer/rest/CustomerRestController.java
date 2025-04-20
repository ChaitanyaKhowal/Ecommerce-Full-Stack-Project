package com.customer.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.customer.dto.CustomerDto;
import com.customer.dto.ResetPwdDto;
import com.customer.response.ApiResponse;
import com.customer.response.AuthResponse;
import com.customer.service.CustomerService;

@RestController
public class CustomerRestController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private BCryptPasswordEncoder pwdEncoder;

	@PostMapping("/register")
	public ResponseEntity<ApiResponse<String>> register(@RequestBody CustomerDto customerDto) {

		ApiResponse<String> response = new ApiResponse<>();

		Boolean emailUnique = customerService.isEmailUnique(customerDto.getCustomerEmail());

		if (!emailUnique) {
			response.setStatus(400);
			response.setMessage("Error");
			response.setData("Duplicate Email");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		Boolean register = customerService.register(customerDto);

		if (register) {
			response.setStatus(201);
			response.setMessage("Success");
			response.setData("Registered successfully");
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} else {
			response.setStatus(500);
			response.setMessage("Error");
			response.setData("Registered failed");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody CustomerDto customerDto) {

		ApiResponse<AuthResponse> response = new ApiResponse<>();

		AuthResponse authResp = customerService.login(customerDto);

		if (authResp != null) {
			response.setStatus(200);
			response.setMessage("Success");
			response.setData(authResp);
			return new ResponseEntity<ApiResponse<AuthResponse>>(response, HttpStatus.OK);
		} else {
			response.setStatus(500);
			response.setMessage("Invalid Credentials");
			response.setData(null);
			return new ResponseEntity<ApiResponse<AuthResponse>>(response, HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/reset-pwd")
	public ResponseEntity<ApiResponse<String>> resetPassword(@RequestBody ResetPwdDto pwdDto) {

		ApiResponse<String> response = new ApiResponse<>();

		CustomerDto customer = customerService.getCustomerByEmail(pwdDto.getCustomerEmail());

		if (!pwdEncoder.matches(pwdDto.getCurrentPassword(), customer.getPassword())) {
			response.setStatus(400);
			response.setMessage("Failed");
			response.setData("Current Password is incorrect");
			return new ResponseEntity<ApiResponse<String>>(response, HttpStatus.BAD_REQUEST);
		}

		Boolean resetPassword = customerService.resetPassword(pwdDto);
		if (resetPassword != null) {
			response.setStatus(200);
			response.setMessage("Success");
			response.setData("Password updated");
			return new ResponseEntity<ApiResponse<String>>(response, HttpStatus.OK);
		} else {
			response.setStatus(500);
			response.setMessage("Failed");
			response.setData("Password reset failed");
			return new ResponseEntity<ApiResponse<String>>(response, HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/forget-pwd/{email}")
	public ResponseEntity<ApiResponse<String>> forgetPassword(@PathVariable String email) {

		ApiResponse<String> response = new ApiResponse<>();

		Boolean status = customerService.forgetPassword(email);
		if (status) {
			response.setStatus(200);
			response.setMessage("Success");
			response.setData("Email sent to reset password");
			return new ResponseEntity<ApiResponse<String>>(response, HttpStatus.OK);
		} else {
			response.setStatus(400);
			response.setMessage("Failed");
			response.setData("No account found");
			return new ResponseEntity<ApiResponse<String>>(response, HttpStatus.BAD_REQUEST);

		}

	}

}
