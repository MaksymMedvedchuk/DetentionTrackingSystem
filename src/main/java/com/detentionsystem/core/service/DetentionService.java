package com.detentionsystem.core.service;

import com.detentionsystem.core.domain.entity.Detention;

public interface DetentionService {

	Detention findByDocNum(String docNum);

	void payOffFineAmount(String docNum, Long fineAmount);
}
