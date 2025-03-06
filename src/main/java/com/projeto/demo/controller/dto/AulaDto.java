package com.projeto.demo.controller.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.projeto.demo.domain.model.Aula;

public record AulaDto(
        Long id,
        String descricao,
        LocalDateTime dataHora,
        InstrutorDto instrutor,
        EstiloDto estilo,
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
