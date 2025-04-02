package com.projeto.demo.controller.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.projeto.demo.domain.model.Aula;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AulaDto(
        Long id,
        @NotNull(message = "Descrição não pode ser nula")
        @Size(min = 1, message = "Descrição deve ter ao menos um caractere")
        String descricao,
        @NotNull(message = "Data e hora não pode ser nula")
        LocalDateTime dataHora,
        @NotNull(message = "Instrutor não pode ser nulo")
        InstrutorDto instrutor,
        @NotNull(message = "Estilo não pode ser nulo")
        EstiloDto estilo,
        @NotNull(message = "Lista de alunos não pode ser nula")
        List<AlunoDto> alunos) {

    public AulaDto(Aula aula) {
        this(
                aula.getId(),
                aula.getDescricao(),
                aula.getDataHora(),
                new InstrutorDto(aula.getInstrutor()),
                new EstiloDto(aula.getEstilo()),
                aula.getAlunos().stream().map(AlunoDto::new).collect(Collectors.toList())
        );
    }

    public Aula toModel() {
        var alunosModel = alunos.stream().map(AlunoDto::toModel).collect(Collectors.toList());
        return new Aula(id, descricao, dataHora, instrutor.toModel(), estilo.toModel(), alunosModel);
    }
}
