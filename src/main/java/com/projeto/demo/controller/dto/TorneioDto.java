package com.projeto.demo.controller.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.projeto.demo.domain.model.Torneio;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TorneioDto(
        Long id,
        @NotNull(message = "Nome não pode ser nulo")
        @Size(min = 1, message = "Nome deve ter ao menos um caractere")
        String nome,
        @NotNull(message = "Data do evento não pode ser nula")
        LocalDate dataEvento,
        EstiloDto estilo,
        @NotNull(message = "Lista de participantes não pode ser nula")
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
