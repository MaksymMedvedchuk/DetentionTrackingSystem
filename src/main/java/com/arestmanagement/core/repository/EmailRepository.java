package com.arestmanagement.core.repository;

import com.arestmanagement.core.domain.entity.Email;
import com.arestmanagement.core.domain.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailRepository extends JpaRepository<Email, Long> {

	Optional<Email> findAllByEmail(String email);
}
