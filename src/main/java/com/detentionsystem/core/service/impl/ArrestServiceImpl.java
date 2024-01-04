package com.detentionsystem.core.service.impl;

import com.detentionsystem.core.domain.entity.Arrest;
import com.detentionsystem.core.exception.ResourceNotfoundException;
import com.detentionsystem.core.repository.ArrestRepository;
import com.detentionsystem.core.service.ArrestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ArrestServiceImpl implements ArrestService {

	private final ArrestRepository arrestRepository;

	public ArrestServiceImpl(final ArrestRepository arrestRepository) {
		this.arrestRepository = arrestRepository;
	}

	@Override
	public Arrest findByDocNum(final String docNum) {
		log.info("In findById trying to find arrset with docNum: [{}]", docNum);
		return arrestRepository.findByDocNum(docNum)
			.orElseThrow(() -> new ResourceNotfoundException("User wasn't found with docNum: " + docNum));
	}
}
