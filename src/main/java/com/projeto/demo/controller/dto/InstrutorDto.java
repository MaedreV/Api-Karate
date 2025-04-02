package com.projeto.demo.controller.dto;

import com.projeto.demo.domain.model.Instrutor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record InstrutorDto(
        Long id,
        @NotNull(message = "Nome não pode ser nulo")
        @Size(min = 1, message = "Nome deve ter ao menos um caractere")
        String nome,
        @NotNull(message = "Estilo não pode ser nulo")
        EstiloDto estilo) {

    public InstrutorDto(Instrutor instrutor) {
        this(instrutor.getId(), instrutor.getNome(), new EstiloDto(instrutor.getEstilo()));
    }

    public Instrutor toModel() {
        return new Instrutor(id, nome, estilo.toModel());
    }
}
