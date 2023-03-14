package com.example.customerarrestsystem.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass //для загальних полів
@Getter
@Setter
@ToString
public class BaseEntity {
    @Id //PK
    @GeneratedValue(strategy = GenerationType.IDENTITY)// генерація ID
    private Long id;
}
