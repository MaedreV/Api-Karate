package com.projeto.demo.controller.dto;

import com.projeto.demo.domain.model.Instrutor;

public record InstrutorDto(
        Long id,
        String nome,
        EstiloDto estilo) {

    public InstrutorDto(Instrutor instrutor) {
        this(instrutor.getId(), instrutor.getNome(), new EstiloDto(instrutor.getEstilo()));
    }

    public Instrutor toModel() {
        return new Instrutor(id, nome, estilo.toModel());
    }
}
