package com.arestmanagement.config;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@Configuration
public class MailConfig {

	private final JavaMailSender javaMailSender;

	public MailConfig(final JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	@Bean
	public MimeMessageHelper mimeMessageHelper() {
		return new MimeMessageHelper(javaMailSender.createMimeMessage());
	}
}
