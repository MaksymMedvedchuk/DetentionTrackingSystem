package com.detentionsystem.core.service.email;

import com.detentionsystem.core.domain.dto.DetentionRequestDto;
import com.detentionsystem.core.domain.entity.Email;
import com.detentionsystem.core.domain.entity.User;
import com.detentionsystem.core.exception.DuplicateEmailException;
import com.detentionsystem.core.repository.EmailRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

	private final EmailRepository emailRepository;

	private final MimeMessageProvider mimeMessageProvider;

	public EmailServiceImpl(
		final EmailRepository emailRepository,
		final MimeMessageProvider mimeMessageProvider
	) {
		this.emailRepository = emailRepository;
		this.mimeMessageProvider = mimeMessageProvider;
	}

	@Override
	public Email saveEmail(final Email email) {
		String currentEmail = email.getEmail();
		if (emailRepository.findAllByEmail(currentEmail).isPresent()) {
			throw new DuplicateEmailException("User with email already exists: " + email);
		}
		log.info("In saveEmail trying to save email with id: [{}]", email.getId());
		return emailRepository.save(email);
	}

	@Override
	public String getCurrentUrl(final HttpServletRequest request) {
		String url = request.getRequestURL().toString();
		return url.replace(request.getServletPath(), "");
	}

	@Override
	public void sendVerificationEmail(final User user, final String siteUrl) {
		mimeMessageProvider.sendEmailConfirmationMessage(user, siteUrl);
	}

	@Override
	public void sendNotificationToPerson(final DetentionRequestDto requestDto) {
		mimeMessageProvider.sendArrestNotification(requestDto);
	}
}
