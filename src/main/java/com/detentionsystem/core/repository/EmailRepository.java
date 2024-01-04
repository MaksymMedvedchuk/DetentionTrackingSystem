package com.detentionsystem.core.repository;

import com.detentionsystem.core.domain.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailRepository extends JpaRepository<Email, Long> {

	Optional<Email> findAllByEmail(String email);
}
