package com.inn.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "APPROVER_USER")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApproverUser extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "USER_NAME")
	private String username;

	@Column(name = "STATUS")
	private String status;

	@Lob
	@Column(name = "REASON")
	private String reason;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PURCHASE_ORDER_DETAIL_ID")
	private PurchaseOrderDetail purchaseOrderDetail;
}

