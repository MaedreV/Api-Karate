package com.projeto.demo.controller.dto;

import java.time.LocalDate;

import com.projeto.demo.domain.model.Aluno;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AlunoDto(
        Long id,
        @NotNull(message = "Nome não pode ser nulo")
        @Size(min = 1, message = "Nome deve ter ao menos um caractere")
        String nome,
        @NotNull(message = "Faixa não pode ser nula")
        String faixa,
        @NotNull(message = "Data de ingresso não pode ser nula")
        LocalDate dataIngresso) {

    public AlunoDto(Aluno aluno) {
        this(aluno.getId(), aluno.getNome(), aluno.getFaixa(), aluno.getDataIngresso());
    }

    public Aluno toModel() {
        return new Aluno(id, nome, faixa, dataIngresso);
    }
}
