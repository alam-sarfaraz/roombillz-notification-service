package com.inn.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.inn.dto.ResponseDto;
import com.inn.repository.INotificationSenderRepository;
import com.inn.service.INotificationSenderService;
import com.inn.service.IPdfGeneratorService;
import com.inn.service.IVelocityTemplateService;

import jakarta.mail.internet.MimeMessage;

@Service
public class NotificationSenderServiceImpl implements INotificationSenderService{
	
	private static final Logger logger = LoggerFactory.getLogger(NotificationSenderServiceImpl.class);
	
	@Autowired
	INotificationSenderRepository notificationRepository;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
    private IVelocityTemplateService templateService;
	
	@Autowired
    private IPdfGeneratorService pdfService;

	@Override
	public ResponseEntity<ResponseDto> sendPurchaseOrderEmail(String[] recipients, Map<String,Object> purchaseModel) {
	    logger.info("Inside sendPurchaseOrderEmail: Recipients={}, Model={}", recipients, purchaseModel);
	    try {
	        // 1. Render HTML template
	        String html = templateService.render("templates/purchase-order.vm", purchaseModel);
	        // 2. PDF from HTML
	        byte[] pdfBytes = pdfService.generatePdfFromHtml(html);
	        // 3. Email
	        MimeMessage message = mailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
	        String purchaseId = (String) ((Map) purchaseModel.get("purchase")).get("purchaseId");
	        helper.setTo("sarfarazalam2702@gmail.com");
	        helper.setSubject("Purchase Order: " + purchaseId);
	        helper.setText(html, true);
	        // Attach PDF ONLY
	        helper.addAttachment("purchase-order-" + purchaseId + ".pdf", new ByteArrayResource(pdfBytes));
	        mailSender.send(message);
	        return ResponseEntity.ok(new ResponseDto("200", "Email sent successfully"));
	    } catch (Exception e) {
	        logger.error("Error in sendPurchaseOrderEmail", e);
	        throw new RuntimeException("Failed to send purchase order email", e);
	    }
	}

	@Override
	public ResponseEntity<ResponseDto> sendPurchaseOrdeApprovedRejectedEmail(String[] recipients, Map<String, Object> model) {
	    logger.info("Inside sendPurchaseOrdeApprovedRejectedEmail: Recipients={}, Model={}", recipients, model);
	    try {
	        String html = templateService.render("templates/pod-status-update.vm", model);
	        byte[] pdfBytes = pdfService.generatePdfFromHtml(html);

	        String purchaseId = (String) model.get("purchaseId");
	        String status = (String) model.get("status");
	        String currentApprover = (String) model.get("currentApprover");

	        MimeMessage message = mailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
	        helper.setTo("sarfarazalam2702@gmail.com");
	        helper.setSubject("Purchase Order " + status + " by " + currentApprover + " - " + purchaseId);
	        helper.setText(html, true);
	        helper.addAttachment("purchase-order-" + purchaseId + ".pdf",new ByteArrayResource(pdfBytes));
	        mailSender.send(message);
	        return ResponseEntity.ok(new ResponseDto("200", "Email sent successfully"));
	    } catch (Exception e) {
	        logger.error("Error in sendPurchaseOrdeApprovedRejectedEmail", e);
	        throw new RuntimeException("Failed to send purchase order approved/rejected email", e);
	    }
	}

	
	


}
