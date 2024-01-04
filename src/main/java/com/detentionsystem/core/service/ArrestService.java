package com.detentionsystem.core.service;

import com.detentionsystem.core.domain.entity.Arrest;

public interface ArrestService {

	Arrest findByDocNum(String docNum);

}
