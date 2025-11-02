package com.inn.notificationUtility;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inn.notificationConstants.NotificationServiceConstant;

public class NotificationServiceUtility {
	
	private static final Logger logger = LoggerFactory.getLogger(NotificationServiceUtility.class);
	
	public static String simpleDateFormatter(String pattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(new Date());
	}

	public static String dateFormatter() {
		return simpleDateFormatter(NotificationServiceConstant.DATE_PATTERN);
	}

	public static String timeFormatter() {
		return simpleDateFormatter(NotificationServiceConstant.TIME_PATTERN);
	}

	public static String modifiedDateTimeFormatter() {
		return simpleDateFormatter(NotificationServiceConstant.MODIFIED_DATE_TIME);
	}

	public static String dateTime() {
		return simpleDateFormatter(NotificationServiceConstant.DATE_TIME);
	}
	
	public static String getFullName(String firstName, String middleName, String lastName) {
		logger.info(NotificationServiceConstant.INSIDE_THE_METHOD + "getFullName");
		StringBuilder fullName = new StringBuilder();

		if (firstName != null && !firstName.trim().isEmpty()) {
			fullName.append(firstName.trim());
		}
		if (middleName != null && !middleName.trim().isEmpty()) {
			if (fullName.length() > 0)
				fullName.append(" ");
			    fullName.append(middleName.trim());
		}
		if (lastName != null && !lastName.trim().isEmpty()) {
			if (fullName.length() > 0)
				fullName.append(" ");
			    fullName.append(lastName.trim());
		}
		return fullName.toString();
	}

}
