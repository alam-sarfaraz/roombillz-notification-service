package com.inn.service;

import java.util.Map;

public interface IVelocityTemplateService {

	public String render(String templatePath, Map<String, Object> model);
}
