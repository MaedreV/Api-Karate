package com.projeto.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.demo.domain.model.Instrutor;

public interface InstrutorRepository extends JpaRepository<Instrutor, Long> {

}
