package com.notifications.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.notifications.entities.CustomerEntity;
import com.notifications.entities.OrderEntity;
import com.notifications.repositories.OrderRepository;

@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private OrderRepository orderRepo;

	@Autowired
	private EmailService emailService;

//	@Value("${wati.token}")
//	private String watiToken;
//
//	@Value("${wati.template.name}")
//	private String templateName;
//
//	@Value("${wati.endpoint.url}")
//	private String watiEndPointUrl;

	@Override
	@Scheduled(cron = "0 7 * * * *")
	public Integer sendDeliveryNotification() {
		List<OrderEntity> orders = orderRepo.findByDeliveryDate(LocalDate.now());

		for (OrderEntity order : orders) {
			CustomerEntity customer = order.getCustomer();

			sendEmailNotification(customer.getCustomerEmail(), order.getOrderTrackingNum());
//			sendWhatsappNotification(customer, order.getOrderTrackingNum());

		}
		return orders.size();
	}

	@Override
	public Integer sendNotificationToPendingOrders() {
		// TODO Auto-generated method stub
		return null;
	}

	// private WatiResponse sendWhatsappNotification(CustomerEntity customer, String
	// orderTrackingNum) {
	//
	// RestTemplate rt = new RestTemplate();
	//
	// String apiUrl = watiEndPointUrl + "?whatsappNumber=91" +
	// customer.getCustomerPhone();
	//
	// WatiParameters nameParam = new WatiParameters();
	// nameParam.setName("name");
	// nameParam.setValue(customer.getCustomerName());
	//
	// WatiParameters trackingParam = new WatiParameters();
	// trackingParam.setName("order_tracking_number");
	// trackingParam.setValue(orderTrackingNum);
	//
	// WatiRequest request = new WatiRequest();
	// request.setTemplate_name(templateName);
	// request.setBroadcast_name(templateName + "BD");
	// request.setParameters(Arrays.asList(nameParam, trackingParam));
	//
	// HttpHeaders headers = new HttpHeaders();
	// headers.add("Authorization", watiToken);
	//
	// HttpEntity<WatiRequest> reqEntity = new HttpEntity<WatiRequest>(request,
	// headers);
	//
	// ResponseEntity<WatiResponse> postForEntity = rt.postForEntity(apiUrl,
	// request, WatiResponse.class);
	//
	// return postForEntity.getBody();
	// }

	private boolean sendEmailNotification(String to, String orderTrackingNum) {

		String subject = "Your Order Out for Delivery";

		String body = """
				<html>
				<body style="font-family: Arial, sans-serif; padding: 20px;">
				    <div style="max-width: 600px; margin: auto; border: 1px solid #ddd; border-radius: 8px; padding: 20px; background-color: #f9f9f9;">
				        <h2 style="color: #333; text-align: center;">Your Order is Out for Delivery</h2>
				        <p>Dear Customer,</p>
				        <p>Your order <strong>%s</strong> will be delivered today.</p>
				        <p>Thank you for shopping with us!</p>
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
				"""
				.formatted(orderTrackingNum);

		return emailService.sendEmail(to, subject, body);
	}

}
