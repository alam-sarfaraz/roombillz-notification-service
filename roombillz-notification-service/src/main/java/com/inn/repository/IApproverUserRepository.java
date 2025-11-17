package com.inn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inn.entity.ApproverUser;

@Repository
public interface IApproverUserRepository extends JpaRepository<ApproverUser, Integer>{
	
	ApproverUser findTopByPurchaseIdOrderByModifiedAtDesc(String purchaseId);

}
