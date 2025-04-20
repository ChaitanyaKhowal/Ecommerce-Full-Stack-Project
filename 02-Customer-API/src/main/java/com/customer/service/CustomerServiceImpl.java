package com.customer.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.customer.dto.CustomerDto;
import com.customer.dto.ResetPwdDto;
import com.customer.entities.CustomerEntity;
import com.customer.mapper.CustomerMapper;
import com.customer.repository.CustomerRepository;
import com.customer.response.AuthResponse;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepo;

	@Autowired
	private BCryptPasswordEncoder pwdEncoder;

	@Autowired
	private EmailService emailService;

	@Autowired
	private AuthenticationManager authManager;

	@Override
	public Boolean isEmailUnique(String emailId) {
		CustomerEntity byEmail = customerRepo.findByCustomerEmail(emailId);
		return byEmail == null;
	}

	@Override
	public Boolean register(CustomerDto customDto) {

		String ogPwd = generateRandomPassword();
		String encodedPwd = pwdEncoder.encode(ogPwd);

		CustomerEntity entity = CustomerMapper.convertToEntity(customDto);
		entity.setPassword(encodedPwd);
		entity.setPasswordUpdated("NO");

		CustomerEntity savedEntity = customerRepo.save(entity);

		if (savedEntity.getCustomerId() != null) {
			String subject = "🎉 Registration Successful - Welcome to Our Platform!";

			String body = String.format(
					"""
							<html>
							<head>
							    <style>
							        body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; }
							        .email-container { max-width: 600px; background: #fff; padding: 20px; border-radius: 10px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); margin: auto; }
							        .header { text-align: center; font-size: 20px; font-weight: bold; color: #333; }
							        .content { padding: 10px; font-size: 16px; color: #555; }
							        .password-box { background: #f8f8f8; padding: 15px; border-radius: 5px; font-size: 18px; font-weight: bold; text-align: center; color: #007BFF; }
							        .footer { margin-top: 20px; text-align: center; font-size: 14px; color: #777; }
							        .social-icons a { margin: 0 10px; text-decoration: none; }
							        .social-icons img { width: 30px; }
							    </style>
							</head>
							<body>
							    <div class='email-container'>
							        <div class='header'>Welcome to Our Platform! 🎊</div>
							        <div class='content'>
							            <p>Dear %s,</p>
							            <p>Thank you for registering! Here are your login details:</p>
							            <div class='password-box'>%s</div>
							            <p>Please change your password after logging in for security purposes.</p>
							        </div>
							        <hr style='margin: 20px 0;'>
							        <p style='text-align: center;'>Need Help? Contact Us:</p>
							        <div class='social-icons' style='text-align: center;'>
							            <a href='tel:+917620085558'>📞</a>
							            <a href='mailto:chaitanyakhowal8@gmail.com'>📧</a>
							            <a href='https://www.linkedin.com/in/chaitanya-khowal-331b47272/'>
							                <img src='https://cdn-icons-png.flaticon.com/512/174/174857.png'>
							            </a>
							            <a href='https://github.com/ChaitanyaKhowal'>
							                <img src='https://cdn-icons-png.flaticon.com/512/25/25231.png'>
							            </a>
							            <a href='https://www.instagram.com/devil_since.2001/'>
							                <img src='https://cdn-icons-png.flaticon.com/512/174/174855.png'>
							            </a>
							        </div>
							        <p style='text-align: center; font-size: 12px; color: #666;'>&copy; 2025 Chaitanya Khowal | All Rights Reserved</p>
							    </div>
							</body>
							</html>
							""",
					customDto.getCustomerName(), ogPwd);

			return emailService.sendEmail(customDto.getCustomerEmail(), subject, body);
		}

		return false;
	}

	@Override
	public Boolean resetPassword(ResetPwdDto pwdDto) {

		CustomerEntity c = customerRepo.findByCustomerEmail(pwdDto.getCustomerEmail());

		if (c != null) {
			String newPassword = pwdDto.getNewPassword();
			String encodedPwd = pwdEncoder.encode(newPassword);
			c.setPassword(encodedPwd);
			c.setPasswordUpdated("YES");

			customerRepo.save(c);
			return true;
		}

		return false;
	}

	@Override
	public CustomerDto getCustomerByEmail(String emailId) {

		CustomerEntity c = customerRepo.findByCustomerEmail(emailId);

		if (c != null) {
			return CustomerMapper.convertToDto(c);
		}
		return null;
	}

	@Override
	public AuthResponse login(CustomerDto customerDto) {

		AuthResponse authResponse = null;

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
				customerDto.getCustomerEmail(), customerDto.getPassword());

		Authentication authenticate = authManager.authenticate(authToken);

		if (authenticate.isAuthenticated()) {
			authResponse = new AuthResponse();
			CustomerEntity c = customerRepo.findByCustomerEmail(customerDto.getCustomerEmail());
			authResponse.setCustomer(CustomerMapper.convertToDto(c));
			authResponse.setToken("");

		}
		return authResponse;

	}

	@Override
	public Boolean forgetPassword(String emailId) {
		CustomerEntity c = customerRepo.findByCustomerEmail(emailId);
		if (c != null) {
			String subject = "Password Reset Request";
			String body = """
					<html>
					<body style="font-family: Arial, sans-serif; padding: 20px;">
					    <div style="max-width: 600px; margin: auto; border: 1px solid #ddd; border-radius: 8px; padding: 20px; background-color: #f9f9f9;">
					        <h2 style="color: #333; text-align: center;">Password Reset Request</h2>
					        <p>Dear User,</p>
					        <p>We received a request to reset your password. Please use the temporary password below:</p>
					        <div style="background: #eee; padding: 10px; text-align: center; font-weight: bold; border-radius: 5px;">
					            YourTemporaryPassword: 123
					        </div>
					        <p>For security reasons, please change your password immediately after logging in.</p>
					        <p>If you did not request this, please ignore this email or contact support.</p>
					        <hr style="margin: 20px 0;">
					        <p style="text-align: center;">Connect with us:</p>
					        <div style="text-align: center;">
					            <a href="https://www.linkedin.com/in/chaitanya-khowal/" style="margin: 0 10px;">
					                <img src="https://cdn-icons-png.flaticon.com/512/174/174857.png" width="30">
					            </a>
					            <a href="https://github.com/ChaitanyaKhowal" style="margin: 0 10px;">
					                <img src="https://cdn-icons-png.flaticon.com/512/25/25231.png" width="30">
					            </a>
					            <a href="https://www.instagram.com/devil_since.2001/" style="margin: 0 10px;">
					                <img src="https://cdn-icons-png.flaticon.com/512/174/174855.png" width="30">
					            </a>
					        </div>
					        <p style="text-align: center; font-size: 12px; color: #666;">&copy; 2025 Chaitanya Khowal | All Rights Reserved</p>
					    </div>
					</body>
					</html>
					""";
			emailService.sendEmail(emailId, subject, body);
			return true;
		}
		return false;
	}

	private String generateRandomPassword() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

		StringBuilder password = new StringBuilder();

		Random random = new Random();

		for (int i = 0; i < 5; i++) {
			int index = (int) (random.nextFloat() * SALTCHARS.length());
			password.append(SALTCHARS.charAt(index));
		}

		String save = password.toString();

		return save;
	}

}
