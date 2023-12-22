package com.arestmanagement.core.repository;

import com.arestmanagement.core.domain.entity.Arrest;
import com.arestmanagement.core.domain.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ArrestRepository extends JpaRepository<Arrest, Long> {

    Optional<Arrest> findByDocNum(String docNum);

    @Query("SELECT a.person FROM Arrest a WHERE id = :arrestId")
    Optional<Person> findPersoneByArrestId(@Param("arrestId")Long arrestId);
}
