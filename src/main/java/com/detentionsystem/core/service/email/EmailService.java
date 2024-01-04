package com.detentionsystem.core.service.email;

import com.detentionsystem.core.domain.dto.ArrestRequestDto;
import com.detentionsystem.core.domain.entity.Email;
import com.detentionsystem.core.domain.entity.User;
import jakarta.servlet.http.HttpServletRequest;

public interface EmailService {

	Email saveEmail(Email email);

	String getCurrentUrl(HttpServletRequest httpServletRequest);

	void sendVerificationEmail(User user, String siteUrl);

	void sendNotificationToPerson(ArrestRequestDto requestDto);
}
