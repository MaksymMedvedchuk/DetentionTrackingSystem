package com.arestmanagement.core.service.email;

import com.arestmanagement.core.domain.dto.ArrestRequestDto;
import com.arestmanagement.core.domain.entity.Email;
import com.arestmanagement.core.domain.entity.User;
import jakarta.servlet.http.HttpServletRequest;

public interface EmailService {

	Email saveEmail(Email email);

	String getCurrentUrl(HttpServletRequest httpServletRequest);

	void sendVerificationEmail(User user, String siteUrl);

	void sendNotificationToPerson(ArrestRequestDto requestDto);
}
