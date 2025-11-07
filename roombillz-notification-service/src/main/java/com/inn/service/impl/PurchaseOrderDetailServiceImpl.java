package com.inn.service.impl;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.inn.client.IUserGroupRegistrationClient;
import com.inn.dto.PurchaseOrderDetailNotificationEvent;
import com.inn.dto.ResponseDto;
import com.inn.entity.ApproverUser;
import com.inn.entity.PurchaseOrderDetail;
import com.inn.notificationConstants.NotificationServiceConstant;
import com.inn.repository.IPurchaseOrderDetailRepository;
import com.inn.service.IPurchaseOrderDetailService;

@Service
public class PurchaseOrderDetailServiceImpl implements IPurchaseOrderDetailService{
	
	private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderDetailServiceImpl.class);
	
	@Autowired
	IPurchaseOrderDetailRepository iPurchaseOrderDetailRepository;
	
	@Autowired
	IUserGroupRegistrationClient iUserGroupRegistrationClient;

	@Override
	public ResponseEntity<ResponseDto> createPurchaseOrder(PurchaseOrderDetailNotificationEvent purchaseOrderDetailNotificationEvent) {
	    try {
	        logger.info(NotificationServiceConstant.INSIDE_THE_METHOD + "createPurchaseOrder {}",kv("PurchaseOrderDetailNotificationEvent", purchaseOrderDetailNotificationEvent));
	        List<String> userList = iUserGroupRegistrationClient.getUserListByGroupName(purchaseOrderDetailNotificationEvent.getGroupName()).getBody();
	        logger.info("User List detail from RoomBillz: {}",kv("UserList", userList));
	        PurchaseOrderDetail purchaseOrderDetail = mapDataToPurchaseOrderDetail(purchaseOrderDetailNotificationEvent);
	        List<ApproverUser> approverUsersList = userList.stream()
	                .map(user -> {
	                    ApproverUser approver = new ApproverUser();
	                    approver.setUsername(user);
	                    approver.setStatus("PENDING");
	                    approver.setPurchaseOrderDetail(purchaseOrderDetail); 
	                    return approver;
	                }).collect(Collectors.toList());
	        
	        purchaseOrderDetail.setApproverUsers(approverUsersList);
	        // Save parent, children will be saved automatically
	        iPurchaseOrderDetailRepository.save(purchaseOrderDetail);
	        logger.info("Purchase Order saved Successfully to Database ...........");
	        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto("201","Purchase Order saved Successfully."));
	    } catch (Exception e) {
	        logger.error(NotificationServiceConstant.ERROR_OCCURRED_DUE_TO, 
	                kv(NotificationServiceConstant.ERROR_MESSAGE, e.getMessage()));
	        throw e;
	    }
	}

	private PurchaseOrderDetail mapDataToPurchaseOrderDetail(PurchaseOrderDetailNotificationEvent purchaseOrderDetailNotificationEvent) {
		logger.info(NotificationServiceConstant.INSIDE_THE_METHOD + "mapDataToPurchaseOrderDetail :");
		PurchaseOrderDetail purchaseOrderDetail = new PurchaseOrderDetail();
		purchaseOrderDetail.setPurchaseId(purchaseOrderDetailNotificationEvent.getPurchaseId());
		purchaseOrderDetail.setPurchaseDate(purchaseOrderDetailNotificationEvent.getPurchaseDate());
		purchaseOrderDetail.setUserName(purchaseOrderDetailNotificationEvent.getUserName());
		purchaseOrderDetail.setFirstName(purchaseOrderDetailNotificationEvent.getFirstName());
		purchaseOrderDetail.setMiddleName(purchaseOrderDetailNotificationEvent.getMiddleName());
		purchaseOrderDetail.setLastName(purchaseOrderDetailNotificationEvent.getLastName());
		purchaseOrderDetail.setEmail(purchaseOrderDetailNotificationEvent.getEmail());
		purchaseOrderDetail.setMobileNumber(purchaseOrderDetailNotificationEvent.getMobileNumber());
		purchaseOrderDetail.setGroupName(purchaseOrderDetailNotificationEvent.getGroupName());
		purchaseOrderDetail.setStatus(purchaseOrderDetailNotificationEvent.getStatus());
		purchaseOrderDetail.setTotalPrice(purchaseOrderDetailNotificationEvent.getTotalPrice());
		purchaseOrderDetail.setModeOfPayment(purchaseOrderDetailNotificationEvent.getModeOfPayment());
		purchaseOrderDetail.setMonth(purchaseOrderDetailNotificationEvent.getMonth());
		return purchaseOrderDetail;
		
	}


}
