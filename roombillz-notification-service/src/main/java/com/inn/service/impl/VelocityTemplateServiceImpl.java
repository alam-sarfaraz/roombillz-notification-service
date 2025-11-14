package com.inn.service.impl;

import static net.logstash.logback.argument.StructuredArguments.kv;

import java.io.StringWriter;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.inn.notificationConstants.NotificationServiceConstant;
import com.inn.service.IVelocityTemplateService;

@Service
public class VelocityTemplateServiceImpl implements IVelocityTemplateService {

	private static final Logger logger = LoggerFactory.getLogger(VelocityTemplateServiceImpl.class);

	private final VelocityEngine velocityEngine;

	public VelocityTemplateServiceImpl(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public String render(String templatePath, Map<String, Object> model) {
		logger.info(NotificationServiceConstant.INSIDE_THE_METHOD + "render :", kv("TemplatePath", templatePath),kv("Model", model));
		try {
			VelocityContext context = new VelocityContext(model);
			StringWriter writer = new StringWriter();
			velocityEngine.mergeTemplate(templatePath, "UTF-8", context, writer);
			return writer.toString();
		} catch (Exception ex) {
			throw new IllegalStateException("Failed to render template: " + templatePath, ex);
		}
	}

}
