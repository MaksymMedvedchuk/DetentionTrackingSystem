package com.detentionsystem.core.service;

import java.time.LocalDateTime;
import java.util.Date;

public interface DataTimeService {

	LocalDateTime now();

	Date toDate(LocalDateTime localDateTime);

	LocalDateTime toLocalDateTime(Date date);
}
