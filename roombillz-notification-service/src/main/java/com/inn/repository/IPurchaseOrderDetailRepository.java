package com.inn.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inn.entity.PurchaseOrderDetail;

@Repository
public interface IPurchaseOrderDetailRepository extends JpaRepository<PurchaseOrderDetail, Integer>{

	public Optional<PurchaseOrderDetail> findByPurchaseId(String purchaseId);

}
