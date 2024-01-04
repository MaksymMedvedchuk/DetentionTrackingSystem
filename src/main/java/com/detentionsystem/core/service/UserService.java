package com.detentionsystem.core.service;

import com.detentionsystem.core.domain.entity.User;

public interface UserService {

	User saveUser(User user);

	void deleteUserById(Long userId);

	User getUserById(Long userId);

	User findUserByEmail(String email);

	void updateIsVariried(Long userId);


}
