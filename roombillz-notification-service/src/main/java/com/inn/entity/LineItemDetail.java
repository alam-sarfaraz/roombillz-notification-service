package com.inn.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LineItemDetail{


	private Integer id;

	private String itemName;

	private Double itemPrice;

	private Double unitPrice;

	private String quantity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PURCHASE_ORDER_ID", nullable = false)
	@JsonBackReference
	@ToString.Exclude
	private PurchaseOrderDetail purchaseOrder;

}
