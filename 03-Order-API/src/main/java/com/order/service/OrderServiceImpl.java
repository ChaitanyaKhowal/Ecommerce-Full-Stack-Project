package com.order.service;

import java.text.SimpleDateFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order.dto.AddressDto;
import com.order.dto.CustomerDto;
import com.order.dto.OrderDto;
import com.order.dto.OrderItemsDto;
import com.order.dto.PaymentCallBackDto;
import com.order.entities.AddressEntity;
import com.order.entities.CustomerEntity;
import com.order.entities.OrderEntity;
import com.order.entities.OrderItems;
import com.order.repositories.AddressRepository;
import com.order.repositories.CustomerRepository;
import com.order.repositories.OrderItemsRepositories;
import com.order.repositories.OrderRepository;
import com.order.request.PurchaseOrderRequest;
import com.order.response.PurchaseOrderResponse;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepo;

	@Autowired
	private OrderItemsRepositories orderItemsRepo;

	@Autowired
	private AddressRepository addRepo;

	@Autowired
	private CustomerRepository customerRepo;

	@Autowired
	private RazorpayService razorpayService;

	@Autowired
	private EmailService emailService;

	@Override
	public PurchaseOrderResponse createOrder(PurchaseOrderRequest orderReq) {

		CustomerDto customerDto = orderReq.getCustomerDto();
		AddressDto addressDto = orderReq.getAddressDto();
		OrderDto orderDto = orderReq.getOrderDto();
		List<OrderItemsDto> orderItemsDtoList = orderReq.getOrderItemsDto();

		if (orderDto == null) {
		    System.err.println("⚠️ OrderDto is null in createOrder()! Check frontend request.");
		    throw new IllegalArgumentException("OrderDto cannot be null in createOrder()");
		}
		
		// Save Customer
		CustomerEntity c = customerRepo.findByCustomerEmail(customerDto.getCustomerEmail());
		if (c == null) {
			// ToDo InterService Communication FeignClient
			c = new CustomerEntity();

			c.setCustomerName(customerDto.getCustomerName());
			c.setCustomerEmail(customerDto.getCustomerEmail());
			c.setCustomerPhone(customerDto.getCustomerPhone());

			customerRepo.save(c);
		}

		// save Address
		AddressEntity addressEntity = new AddressEntity();
		addressEntity.setHouseNum(addressDto.getHouseNum());
		addressEntity.setStreet(addressDto.getStreet());
		addressEntity.setCity(addressDto.getCity());
		addressEntity.setState(addressDto.getState());
		addressEntity.setZipcode(addressDto.getZipcode());
		addressEntity.setCustomer(c);

		addRepo.save(addressEntity);

		// Save Order
		OrderEntity newOrder = new OrderEntity();
		String orderTrackingNum = generateOrderTrackingNum();
		newOrder.setOrderTrackingNum(orderTrackingNum);

		// Creating Razorpay order and getting order details
		com.razorpay.Order paymentOrder = razorpayService.createPaymentOrder(orderDto.getTotalPrice());

		newOrder.setRazorPayOrderId(paymentOrder.get("id"));
		newOrder.setOrderStatus(paymentOrder.get("status"));
		newOrder.setTotalPrice(orderDto.getTotalPrice());
		newOrder.setTotalQuantity(orderDto.getTotalQuantity());
		newOrder.setEmail(c.getCustomerEmail());

		newOrder.setCustomer(c); // Association Mapping
		newOrder.setAddress(addressEntity); // Association Mapping

		orderRepo.save(newOrder);

		// Save Order Items
		for (OrderItemsDto itemDto : orderItemsDtoList) {
			OrderItems item = new OrderItems();
			BeanUtils.copyProperties(itemDto, item);
			item.setOrder(newOrder); // Association Mapping
			orderItemsRepo.save(item);
		}

		// Prepare and return response
		return PurchaseOrderResponse.builder().razorPayOrderId(paymentOrder.get("id"))
				.orderStatus(paymentOrder.get("status")).orderTrackingNum(orderTrackingNum).build();

	}

	@Override
	public PurchaseOrderResponse updateOrder(PaymentCallBackDto paymentCallBackDto) {

		OrderEntity order = orderRepo.findByRazorPayOrderId(paymentCallBackDto.getRazorPayOrderId());

		if (order != null) {
			order.setOrderStatus("CONFIRMED");
			order.setDeliveryDate(LocalDate.now().plusDays(4));
			order.setRazorPayPaymentId(paymentCallBackDto.getRazorPayPaymentId());

			orderRepo.save(order);

			String subject = "🎉 Your Order is Confirmed!";
			String body = "<html>"
					+ "<body style='font-family: Arial, sans-serif; padding: 20px; background-color: #f4f4f4;'>"
					+ "<div style='max-width: 600px; margin: auto; background: #fff; padding: 20px; border-radius: 10px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);'>"
					+ "<h2 style='color: #28a745;'>Thank You for Your Order! 🎊</h2>"
					+ "<p style='font-size: 16px; color: #333;'>We are happy to inform you that your order is confirmed.</p>"
					+ "<p style='font-size: 16px; color: #333;'>Expected Delivery Date: <strong>"
					+ order.getDeliveryDate() + "</strong></p>" + "<hr style='border: 1px solid #ddd;'>"
					+ "<p style='font-size: 14px; color: #666;'>For any queries, feel free to contact us:</p>"
					+ "<div style='text-align: center;'>"
					+ "<a href='mailto:chaitanyakhowal8@gmail.com' style='margin: 0 10px;'><img src='https://img.icons8.com/fluency/48/000000/email.png'/></a>"
					+ "<a href='https://www.linkedin.com/in/chaitanya-khowal-331b47272/' style='margin: 0 10px;'><img src='https://img.icons8.com/fluency/48/000000/linkedin.png'/></a>"
					+ "<a href='https://github.com/ChaitanyaKhowal' style='margin: 0 10px;'><img src='https://img.icons8.com/fluency/48/000000/github.png'/></a>"
					+ "<a href='https://www.instagram.com/devil_since.2001/' style='margin: 0 10px;'><img src='https://img.icons8.com/fluency/48/000000/instagram-new.png'/></a>"
					+ "</div>" + "<p style='font-size: 14px; color: #666;'>Best Regards,<br>Chaitanya Khowal</p>"
					+ "</div>" + "</body>" + "</html>";

			emailService.sendEmail(order.getEmail(), subject, body);

			// Prepare and return response
			return PurchaseOrderResponse.builder().razorPayOrderId(paymentCallBackDto.getRazorPayOrderId())
					.orderStatus(order.getOrderStatus()).orderTrackingNum(order.getOrderTrackingNum()).build();

		}

		return null;
	}

	@Override
	public List<OrderDto> getOrdersByEmail(String email) {

		List<OrderDto> dtoList = new ArrayList<>();

		List<OrderEntity> ordersList = orderRepo.findByEmail(email);

		for (OrderEntity order : ordersList) {
			OrderDto dto = new OrderDto();
			BeanUtils.copyProperties(order, dto);
			dtoList.add(dto);
		}
		return dtoList;
	}

	private String generateOrderTrackingNum() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String timestamp = sdf.format(new Date());

		String randmUuid = UUID.randomUUID().toString().substring(0, 5).toUpperCase();

		// Combine Timestamp and UUID to form order tracking number
		return "OD" + timestamp + randmUuid;

	}

}
