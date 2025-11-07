package com.inn.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PURCHASE_ORDER_DETAIL")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderDetail extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "PURCHASE_ID")
	private String purchaseId;

	@PastOrPresent(message = "Purchase Date must be in the current and Past")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name = "PURCHASE_DATE")
	private LocalDate purchaseDate;

	@Column(name = "USER_NAME")
	private String userName;

	@Column(name = "USER_ID")
	private String userId;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "MIDDLE_NAME")
	private String middleName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "MOBILE_NUMBER")
	private String mobileNumber;

	@Column(name = "GROUP_ID")
	private String groupId;

	@Column(name = "GROUP_NAME")
	private String groupName;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "TOTAL_PRICE")
	private Double totalPrice;

	@Column(name = "MODE_OF_PAYMENT")
	private String modeOfPayment;
	
	@Column(name = "MONTH")
	private String month;
}