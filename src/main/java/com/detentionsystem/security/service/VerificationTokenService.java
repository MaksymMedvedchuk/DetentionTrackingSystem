package com.detentionsystem.security.service;

import com.detentionsystem.core.domain.entity.Token;
import com.detentionsystem.core.domain.entity.User;

public interface VerificationTokenService {

	Token generateVerificationToken(User user);

}
