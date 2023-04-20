package com.arestmanagement.repository;

import com.arestmanagement.entity.Arrest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ArrestRepository extends JpaRepository<Arrest, Long> {

    Optional<Arrest> findByDocNum(String docNum);
}
