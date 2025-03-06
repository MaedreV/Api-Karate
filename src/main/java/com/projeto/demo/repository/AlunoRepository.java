package com.projeto.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.demo.domain.model.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
}
