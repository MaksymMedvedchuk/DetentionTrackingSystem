package com.arestmanagement.core.service.impl;

import com.arestmanagement.core.domain.entity.User;
import com.arestmanagement.core.exception.DuplicateEmailException;
import com.arestmanagement.core.exception.ResourceNotfoundException;
import com.arestmanagement.core.repository.TokenRepository;
import com.arestmanagement.core.repository.UserRepository;
import com.arestmanagement.core.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	private final TokenRepository tokenRepository;

	public UserServiceImpl(final UserRepository userRepository, final BCryptPasswordEncoder bCryptPasswordEncoder,
		final TokenRepository tokenRepository
	) {
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.tokenRepository = tokenRepository;
	}

	@Override
	public User saveUser(final User user) {
		String email = user.getEmail();
		if (userRepository.findAllByEmail(email).isPresent()){
			throw new DuplicateEmailException("User with email already exists: [{}]" + email);
		}
		if (user.getPassword() != null) {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		}
		log.debug("In saveUser trying save user with id: [{}]", user.getId());
		return userRepository.save(user);
	}

	@Override
	@Transactional
	public void deleteUserById(final Long userId) {
		log.debug("In deleteUserById trying delete user and all tokens with id: [{}]", userId);
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new ResourceNotfoundException("User wasn't found with id: " + userId));
		tokenRepository.deleteAllByUserId(user.getId());
		userRepository.deleteById(user.getId());
	}

	@Override
	public User getUserById(final Long userId) {
		log.debug("In getUserById trying find user with id: [{}]", userId);
		return userRepository.findById(userId)
			.orElseThrow(() -> new ResourceNotfoundException("User with id: " + userId + " not found"));
	}

	@Override
	public User findUserByEmail(final String email) {
		log.debug("In findUserByEmail trying find user with email: [{}]", email);
	return userRepository.findAllByEmail(email)
			.orElseThrow(() -> new ResourceNotfoundException("User wasn't found with email: " + email));
	}

	@Override
	@Transactional
	public void updateIsVariried(final Long userId) {
		log.info("In updateIsVarirield trying update verified user with id: [{}]", userId);
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new ResourceNotfoundException("User wasn't found with id: " + userId));
		user.setVerified(true);
		userRepository.save(user);
	}
}
