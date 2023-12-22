package com.arestmanagement.core.service.email;

import com.arestmanagement.core.domain.dto.ArrestRequestDto;
import com.arestmanagement.core.domain.entity.User;
import com.arestmanagement.core.exception.MailException;
import com.arestmanagement.security.service.VerificationTokenService;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MimeMessageProvider {

	private static final String SENDER_NAME = "Arrest";

	private final MimeMessageHelper mimeMessageHelper;

	private final String fromAddress;

	private final String varificationEndpoint;

	private final JavaMailSender javaMailSender;

	private final VerificationTokenService verificationTokenService;

	public MimeMessageProvider(
		final @Value("${email.activate.endpoint}") String varificationEndpoint,
		final @Value("${email.from}") String fromAddress,
		final MimeMessageHelper mimeMessageHelper,
		final JavaMailSender javaMailSender,
		final VerificationTokenService verificationTokenService
	) {
		this.mimeMessageHelper = mimeMessageHelper;
		this.javaMailSender = javaMailSender;
		this.verificationTokenService = verificationTokenService;
		this.fromAddress = fromAddress;
		this.varificationEndpoint = varificationEndpoint;
	}

	public void sendEmailConfirmationMessage(final User user, final String siteUrl) {
		createEmailConfirmationMessage(user, siteUrl);
		log.info("In sendMessage sent message to email: [{}]", user.getEmail());
		javaMailSender.send(mimeMessageHelper.getMimeMessage());
	}

	private void createEmailConfirmationMessage(final User user, final String siteUrl) {
		final String toAddress = user.getEmail();
		final String code = verificationTokenService.generateVerificationToken(user).getTokenValue();
		final String enpoindUrl = siteUrl + varificationEndpoint;
		final String subject = "Please verify your registration";
		final String content = "<html><body>" +
			"<p>Dear " + user.getFirstName() + ",</p>" +
			"<p>Please click the button below to verify your registration:</p>" +
			"<form action='" + enpoindUrl + "' method='post'>" +
			"<input type='hidden' name='code' value='" + code + "'>" +
			"<input type='submit' value='VERIFY'>" +
			"</form>" +
			"<p>Thank you!</p>" +
			"</body></html>";
		configureMimeMessage(toAddress, subject, content);
	}

	private void createArrestNotification(final ArrestRequestDto requestDto) {
		final String toAddress = requestDto.getEmail();
		final String subject = "You received arrest";
		final String
			content =
			"Hello, " +
				requestDto.getFirstName() +
				" you received an arrest for the amount " +
				requestDto.getArrestDto().getAmount();
		configureMimeMessage(toAddress, subject, content);
	}

	private void configureMimeMessage(final String toAddress, final String subject, final String content) {
		try {
			mimeMessageHelper.setFrom(fromAddress, SENDER_NAME);
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setTo(toAddress);
			mimeMessageHelper.setText(content, true);
		} catch (MessagingException e) {
			log.warn("In configureMimeMessage exception: [{}]", e.getMessage());
			throw new RuntimeException(e);
		} catch (Exception e) {
			throw new MailException("Error send message to user with email: " + toAddress);
		}
	}

	public void sendArrestNotification(final ArrestRequestDto requestDto) {
		createArrestNotification(requestDto);
		log.info("In sendArrestNotification sent notification about arrest to email: [{}]", requestDto.getEmail());
		javaMailSender.send(mimeMessageHelper.getMimeMessage());
	}
}
