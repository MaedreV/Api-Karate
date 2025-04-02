package com.projeto.demo.controller.dto;

import com.projeto.demo.domain.model.Estilo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EstiloDto(
        Long id,
        @NotNull(message = "Nome não pode ser nulo")
        @Size(min = 1, message = "Nome deve ter ao menos um caractere")
        String nome,
        @NotNull(message = "Descrição não pode ser nula")
        @Size(min = 1, message = "Descrição deve ter ao menos um caractere")
        String descricao) {

    public EstiloDto(Estilo estilo) {
        this(estilo.getId(), estilo.getNome(), estilo.getDescricao());
    }

    public Estilo toModel() {
        return new Estilo(id, nome, descricao);
    }
}
