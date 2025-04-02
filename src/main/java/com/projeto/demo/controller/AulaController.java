package com.projeto.demo.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.projeto.demo.controller.dto.AulaDto;
import com.projeto.demo.service.AulaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/aulas")
@Tag(name = "Aulas Controller", description = "RESTful API for aulas.")
public record AulaController(AulaService aulaService) {

    @GetMapping
    @Operation(summary = "Recuperar todas as aulas", description = "Recuperar uma lista com todas as aulas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucesso")
    })
    public ResponseEntity<List<AulaDto>> findAll() {
        var aulas = aulaService.findAll();
        var aulasDto = aulas.stream().map(AulaDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(aulasDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Recuperar aula", description = "Recuperar aula usando id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucesso"),
        @ApiResponse(responseCode = "404", description = "Aula não encontrada")
    })
    public ResponseEntity<AulaDto> findById(@PathVariable Long id) {
        var aula = aulaService.findById(id);
        return ResponseEntity.ok(new AulaDto(aula));
    }

    @PostMapping
    @Operation(summary = "Criar nova aula", description = "Criar nova aula com sucesso")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Aula criada"),
        @ApiResponse(responseCode = "422", description = "Dados de aula invalida")
    })
    public ResponseEntity<AulaDto> create(@RequestBody @Valid AulaDto aulaDto) {
        var aula = aulaService.create(aulaDto.toModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(aula.getId())
                .toUri();
        return ResponseEntity.created(location).body(new AulaDto(aula));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar aula", description = "Atualizar aula pelo id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Aula atualizada"),
        @ApiResponse(responseCode = "404", description = "Aula não encontrada"),
        @ApiResponse(responseCode = "422", description = "Dados de aula invalida")
    })
    public ResponseEntity<AulaDto> update(@PathVariable Long id, @RequestBody @Valid AulaDto aulaDto) {
        var aula = aulaService.update(id, aulaDto.toModel());
        return ResponseEntity.ok(new AulaDto(aula));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar aula", description = "Deletar aula pelo id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Aula deleteda com sucesso"),
        @ApiResponse(responseCode = "404", description = "Aula não encontrada")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        aulaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
