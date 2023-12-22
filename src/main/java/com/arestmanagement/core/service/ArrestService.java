package com.arestmanagement.core.service;

import com.arestmanagement.core.domain.entity.Arrest;

public interface ArrestService {

	Arrest findByDocNum(String docNum);

}
