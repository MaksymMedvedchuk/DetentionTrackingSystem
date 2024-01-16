package com.detentionsystem.core.service.impl;

import com.detentionsystem.core.domain.entity.Detention;
import com.detentionsystem.core.exception.ResourceNotfoundException;
import com.detentionsystem.core.repository.DetentionRepository;
import com.detentionsystem.core.service.DetentionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DetentionServiceImpl implements DetentionService {

	private final DetentionRepository detentionRepository;

	public DetentionServiceImpl(final DetentionRepository detentionRepository) {
		this.detentionRepository = detentionRepository;
	}

	@Override
	public Detention findByDocNum(final String docNum) {
		log.info("In findByDocNum trying to find detention with docNum: [{}]", docNum);
		return detentionRepository.findByDocNum(docNum)
			.orElseThrow(() -> new ResourceNotfoundException("User wasn't found with docNum: " + docNum));
	}
}
