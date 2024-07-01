package com.hitrsutpay.sa.backend.constant;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;

public class ConstantManager {
	private static final ConstantManager instance = new ConstantManager();
	private Properties properties;

	private ConstantManager() {
		try {
			// Load the properties file
			ClassPathResource resource = new ClassPathResource("constant.properties");
			properties = new Properties();
			properties.load(resource.getInputStream());
		} catch (IOException e) {
			throw new RuntimeException("Error loading constant file", e);
		}
	}

	public static ConstantManager getInstance() {
		return instance;
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}
}
