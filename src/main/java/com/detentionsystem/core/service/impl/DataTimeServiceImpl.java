package com.detentionsystem.core.service.impl;

import com.detentionsystem.core.service.DataTimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


@Slf4j
@Service
public class DataTimeServiceImpl implements DataTimeService {

	@Override
	public LocalDateTime now() {
		return LocalDateTime.now();
	}

	@Override
	public Date toDate(final LocalDateTime localDateTime) {
		log.trace("Converting LocalDateTime to Date: {}", localDateTime);
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	@Override
	public LocalDateTime toLocalDateTime(final Date date) {
		return Instant.ofEpochMilli(date.getTime())
			.atZone(ZoneId.systemDefault())
			.toLocalDateTime();
	}
}
