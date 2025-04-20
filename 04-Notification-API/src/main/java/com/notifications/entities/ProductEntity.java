package com.notifications.entities;

import java.math.BigDecimal;
import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "product")
public class ProductEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer productId;

	private String productName;
	private String productDesc;
	private String productTitle;
	private BigDecimal unitPrice;
	private String imageUrl;
	private Boolean active;
	private Integer unitsStock;

//	@CreationTimestamp
//	@Column(updatable = false)
//	private LocalDate dateCreated;
	private Date dateCreated;

//	@UpdateTimestamp
//	@Column(insertable = false)
//	private LocalDate lastUpdated;
	private Date lastUpdated;
	
	@ManyToOne
	@JoinColumn(name = "categoryId")
	private CategoryEntity category;

}
