package com.order.entities;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class OrderEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderId;

	private String orderTrackingNum;
	private String razorPayOrderId;
	private String email;
	private String orderStatus;
	private Double totalPrice;
	private Integer totalQuantity;
	private String razorPayPaymentId;
	private String invoiceUrl;
	private LocalDate deliveryDate;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDate dateCreated;

	@UpdateTimestamp
	@Column(insertable = false)
	private LocalDate lastUpdated;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private CustomerEntity customer;

	@ManyToOne
	@JoinColumn(name = "address_id")
	private AddressEntity address;

}
