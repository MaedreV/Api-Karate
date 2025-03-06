package com.projeto.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.demo.domain.model.Aula;

public interface AulaRepository extends JpaRepository<Aula, Long> {

}
