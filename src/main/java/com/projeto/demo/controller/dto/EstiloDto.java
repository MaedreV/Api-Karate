package com.projeto.demo.controller.dto;

import com.projeto.demo.domain.model.Estilo;

public record EstiloDto(
        Long id,
        String nome,
        String descricao) {

    public EstiloDto(Estilo estilo) {
        this(estilo.getId(), estilo.getNome(), estilo.getDescricao());
    }

    public Estilo toModel() {
        return new Estilo(id, nome, descricao);
    }
}
