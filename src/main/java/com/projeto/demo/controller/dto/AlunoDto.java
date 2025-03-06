package com.projeto.demo.controller.dto;

import java.time.LocalDate;

import com.projeto.demo.domain.model.Aluno;

public record AlunoDto(
        Long id,
        String nome,
        String faixa,
        LocalDate dataIngresso) {

    public AlunoDto(Aluno aluno) {
        this(aluno.getId(), aluno.getNome(), aluno.getFaixa(), aluno.getDataIngresso());
    }

    public Aluno toModel() {
        return new Aluno(id, nome, faixa, dataIngresso);
    }
}
