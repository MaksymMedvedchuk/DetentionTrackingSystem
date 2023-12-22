package com.arestmanagement.core.repository;

import com.arestmanagement.core.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findAllByEmail(String email);

}
