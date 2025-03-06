package com.projeto.demo.controller.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.projeto.demo.domain.model.Torneio;

public record TorneioDto(
        Long id,
        String nome,
        LocalDate dataEvento,
        EstiloDto estilo,
        List<AlunoDto> participantes) {

    public TorneioDto(Torneio torneio) {
        this(
                torneio.getId(),
                torneio.getNome(),
                torneio.getDataEvento(),
                torneio.getEstilo() != null ? new EstiloDto(torneio.getEstilo()) : null,
                torneio.getParticipantes().stream().map(AlunoDto::new).collect(Collectors.toList())
        );
    }

    public Torneio toModel() {
        var participantesModel = participantes.stream().map(AlunoDto::toModel).collect(Collectors.toList());
        return new Torneio(id, nome, dataEvento, estilo != null ? estilo.toModel() : null, participantesModel);
    }
}
