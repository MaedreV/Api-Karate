package com.projeto.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.demo.domain.model.Torneio;

public interface TorneioRepository extends JpaRepository<Torneio, Long> {

}
