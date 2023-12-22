package com.arestmanagement.core.service.impl;

import com.arestmanagement.core.domain.entity.Arrest;
import com.arestmanagement.core.exception.ResourceNotfoundException;
import com.arestmanagement.core.repository.ArrestRepository;
import com.arestmanagement.core.service.ArrestService;
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
