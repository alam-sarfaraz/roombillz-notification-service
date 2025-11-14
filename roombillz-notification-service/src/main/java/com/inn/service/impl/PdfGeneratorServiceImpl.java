package com.inn.service.impl;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.io.ByteArrayOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.inn.notificationConstants.NotificationServiceConstant;
import com.inn.service.IPdfGeneratorService;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

@Service
public class PdfGeneratorServiceImpl implements IPdfGeneratorService {

	private static final Logger logger = LoggerFactory.getLogger(PdfGeneratorServiceImpl.class);

	@Override
	public byte[] generatePdfFromHtml(String html) {
		logger.info(NotificationServiceConstant.INSIDE_THE_METHOD + "generatePdfFromHtml :", kv("Html", html));
		 try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
	            PdfRendererBuilder builder = new PdfRendererBuilder();
	            builder.useFastMode();
	            builder.withHtmlContent(html, null);
	            builder.toStream(outputStream);
	            builder.run();
	            return outputStream.toByteArray();
	        } catch (Exception e) {
	            throw new RuntimeException("PDF generation failed", e);
	        }
	}

}
