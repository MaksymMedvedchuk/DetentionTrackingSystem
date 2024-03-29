package com.detentionsystem.core.repository;

import com.detentionsystem.core.domain.entity.Person;
import com.detentionsystem.core.domain.enums.InternalIdentityDocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

	@Query("SELECT p FROM Person p WHERE p.firstName = :firstName AND p.lastName = :lastName " +
		"AND p.identDocType = :identDocType AND p.docNumberSeries = :docNumberSeries")
	Optional<Person> findPerson(@Param("firstName") String firstName,
		@Param("lastName") String lastName,
		@Param("identDocType") InternalIdentityDocumentType docType,
		@Param("docNumberSeries") String docNumberSeries);


}
