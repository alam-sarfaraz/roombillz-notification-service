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

import com.inn.client.IPurchaseOrderDetailClient;
import com.inn.client.IUserGroupRegistrationClient;
import com.inn.customException.PurchaseOrderNotFoundException;
import com.inn.dto.ApproveRejectDTO;
import com.inn.dto.PurchaseOrderDetailNotificationEvent;
import com.inn.dto.ResponseDto;
import com.inn.entity.ApproverUser;
import com.inn.entity.PurchaseOrderDetail;
import com.inn.notificationConstants.NotificationServiceConstant;
import com.inn.repository.IPurchaseOrderDetailRepository;
import com.inn.service.IPurchaseOrderDetailService;

import jakarta.transaction.Transactional;

@Service
public class PurchaseOrderDetailServiceImpl implements IPurchaseOrderDetailService{
	
	private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderDetailServiceImpl.class);
	
	@Autowired
	IPurchaseOrderDetailRepository iPurchaseOrderDetailRepository;
	
	@Autowired
	IUserGroupRegistrationClient iUserGroupRegistrationClient;
	
	@Autowired
	IPurchaseOrderDetailClient iPurchaseOrderDetailClient; 

	@Override
	public ResponseEntity<ResponseDto> createPurchaseOrder(PurchaseOrderDetailNotificationEvent purchaseOrderDetailNotificationEvent) {
	    try {
	        logger.info(NotificationServiceConstant.INSIDE_THE_METHOD + "createPurchaseOrder {}",kv("PurchaseOrderDetailNotificationEvent", purchaseOrderDetailNotificationEvent));
	     // Validate if Purchase Order already exists
	        if (iPurchaseOrderDetailRepository.findByPurchaseId(purchaseOrderDetailNotificationEvent.getPurchaseId()).isPresent()) {
	            logger.warn("Purchase Order already exists for PurchaseId: {}", purchaseOrderDetailNotificationEvent.getPurchaseId());
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("400", "Purchase Order detail already exists"));
	        }
	        List<String> userList = iUserGroupRegistrationClient.getUserListByGroupName(purchaseOrderDetailNotificationEvent.getGroupName()).getBody();
	        logger.info("User List detail from RoomBillz: {}",kv("UserList", userList));
	        PurchaseOrderDetail purchaseOrderDetail = mapDataToPurchaseOrderDetail(purchaseOrderDetailNotificationEvent);
	        List<ApproverUser> approverUsersList = userList.stream()
	                .map(user -> {
	                    ApproverUser approver = new ApproverUser();
	                    approver.setUsername(user);
	                    approver.setStatus(NotificationServiceConstant.PENDING);
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

	@Override
	public ResponseEntity<ResponseDto> approveRejectPurchaseOrderDetail(ApproveRejectDTO approveRejectDTO) {
	    try {
	        logger.info(NotificationServiceConstant.INSIDE_THE_METHOD + "approveRejectPurchaseOrderDetail {}",kv("ApproveRejectDTO", approveRejectDTO));

	        PurchaseOrderDetail pod = iPurchaseOrderDetailRepository.findByPurchaseId(approveRejectDTO.getPurchaseId())
	                                  .orElseThrow(() -> new PurchaseOrderNotFoundException("Purchase Order Detail ","PurchaseId",approveRejectDTO.getPurchaseId()));

	        List<ApproverUser> approverUsersList = pod.getApproverUsers();
	        // Find existing user record
	        ApproverUser approver = approverUsersList.stream()
	                .filter(e -> e.getUsername().equalsIgnoreCase(approveRejectDTO.getUsername()))
	                .findFirst()
	                .orElseThrow(() ->  new PurchaseOrderNotFoundException("Approver User Detail","Approver Username",approveRejectDTO.getUsername()));

	        // Update status and reason
	        approver.setStatus(approveRejectDTO.getStatus());
	        approver.setReason(approveRejectDTO.getReason());
	        iPurchaseOrderDetailRepository.save(pod);
	        logger.info("Purchase Order updated successfully.");
	        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("200", "Purchase Order updated successfully."));
	    } catch (Exception e) {
	        logger.error(NotificationServiceConstant.ERROR_OCCURRED_DUE_TO,
	                kv(NotificationServiceConstant.ERROR_MESSAGE, e.getMessage()));
	        throw e;
	    }
	}

	@Override
	@Transactional
	public ResponseEntity<ResponseDto> updateApproveRejectPurchaseOrderDetailStatus() {
	    try {
	        logger.info(NotificationServiceConstant.INSIDE_THE_METHOD + "updateApproveRejectPurchaseOrderDetailStatus");

	        List<PurchaseOrderDetail> podList = iPurchaseOrderDetailRepository.findAll();
	        if (podList == null || podList.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("400", "Purchase Order Details not found."));
	        }

	        for (PurchaseOrderDetail pod : podList) {
	            List<ApproverUser> approvers = pod.getApproverUsers();

	            if (approvers == null || approvers.isEmpty()) {
	                continue;
	            }
	            
	            boolean anyRejected = approvers.stream()
	                    .anyMatch(a -> NotificationServiceConstant.REJECTED.equalsIgnoreCase(a.getStatus()));

	            boolean allApproved = approvers.stream()
	                    .allMatch(a -> NotificationServiceConstant.APPROVED.equalsIgnoreCase(a.getStatus()));

	            boolean anyApproved = approvers.stream()
	                    .anyMatch(a -> NotificationServiceConstant.APPROVED.equalsIgnoreCase(a.getStatus()));
	            
	            String finalStatus;
	            if (anyRejected) {
	                finalStatus = NotificationServiceConstant.REJECTED;
	            } else if (allApproved) {
	                finalStatus =  NotificationServiceConstant.APPROVED;
	            } else if (anyApproved) {
	                finalStatus = NotificationServiceConstant.PARTIALLY_APPROVED;
	            } else {
	                finalStatus = NotificationServiceConstant.WAITING_FOR_APPROVAL;
	            }

	            pod.setStatus(finalStatus);
	            
	            // Update status to RoomBillz Purchase Order Detail Status.
	            iPurchaseOrderDetailClient.updatePODetailStatusByPurchaseId(pod.getPurchaseId(), finalStatus);
	            
	            iPurchaseOrderDetailRepository.save(pod);
	        }
	        logger.info("Purchase Order statuses updated successfully.");
	        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("200", "Purchase Order statuses updated successfully."));
	    } catch (Exception e) {
	        logger.error(NotificationServiceConstant.ERROR_OCCURRED_DUE_TO,
	                kv(NotificationServiceConstant.ERROR_MESSAGE, e.getMessage()));
	        throw e;
	    }
	}

	@Override
	public ResponseEntity<ResponseDto> deleteAllPurchaseOrderDetails() {
		try {
			logger.info(NotificationServiceConstant.INSIDE_THE_METHOD + "deleteAllPurchaseOrderDetails");
			iPurchaseOrderDetailRepository.deleteAll();
			return ResponseEntity.ok(new ResponseDto("200", "Purchase Order detail(s) deleted processed successfully")); 
		} catch (Exception e) {
			logger.error(NotificationServiceConstant.ERROR_OCCURRED_DUE_TO, kv(NotificationServiceConstant.ERROR_MESSAGE, e.getMessage()));
			throw e;
		}
	}



}
