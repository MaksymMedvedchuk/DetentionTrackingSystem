package com.detentionsystem.core.repository;

import com.detentionsystem.core.domain.entity.Detention;
import com.detentionsystem.core.domain.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DetentionRepository extends JpaRepository<Detention, Long> {

	Optional<Detention> findByDocNum(String docNum);

/*	@Query("SELECT a.person FROM Detention a WHERE id = :detentionId")
	Optional<Person> findPersonByDetentionId(@Param("arrestId") Long detentionId);*/
}
