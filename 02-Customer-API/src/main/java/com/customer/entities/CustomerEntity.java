package com.customer.entities;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "customer")
public class CustomerEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer customerId;

	private String customerName;
	private String customerEmail;
	private Long customerPhone;
	private String password;
	private String passwordUpdated;
	
	@CreationTimestamp
	@Column(updatable = false)
	private LocalDate createdAt;
	
	@UpdateTimestamp
	@Column(insertable = false)
	private LocalDate lastUpdated;

}
