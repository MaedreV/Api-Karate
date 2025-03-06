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

import com.projeto.demo.controller.dto.InstrutorDto;
import com.projeto.demo.service.InstrutorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin
@RestController
@RequestMapping("/instrutores")
@Tag(name = "Instrutores Controller", description = "RESTful API for instrutores.")
public record InstrutorController(InstrutorService instrutorService) {

    @GetMapping
    @Operation(summary = "Recuperar instrutores", description = "Recuperar uma lista com os instrutores")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucesso")
    })
    public ResponseEntity<List<InstrutorDto>> findAll() {
        var instrutores = instrutorService.findAll();
        var instrutoresDto = instrutores.stream().map(InstrutorDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(instrutoresDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Recuperar instrutor", description = "Recuperar instrutor pelo id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucesso"),
        @ApiResponse(responseCode = "404", description = "Instrutor não encontrado")
    })
    public ResponseEntity<InstrutorDto> findById(@PathVariable Long id) {
        var instrutor = instrutorService.findById(id);
        return ResponseEntity.ok(new InstrutorDto(instrutor));
    }

    @PostMapping
    @Operation(summary = "Criar novo instrutor", description = "Criar e retornar novo instrutor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Instrutor criado"),
        @ApiResponse(responseCode = "422", description = "Dados de instrutor invalido")
    })
    public ResponseEntity<InstrutorDto> create(@RequestBody InstrutorDto instrutorDto) {
        var instrutor = instrutorService.create(instrutorDto.toModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(instrutor.getId())
                .toUri();
        return ResponseEntity.created(location).body(new InstrutorDto(instrutor));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar instrutor", description = "Atualizar instrutor pelo id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Instrutor atualizado"),
        @ApiResponse(responseCode = "404", description = "Instrutor não encontrado"),
        @ApiResponse(responseCode = "422", description = "Dados de instrutor invalido")
    })
    public ResponseEntity<InstrutorDto> update(@PathVariable Long id, @RequestBody InstrutorDto instrutorDto) {
        var instrutor = instrutorService.update(id, instrutorDto.toModel());
        return ResponseEntity.ok(new InstrutorDto(instrutor));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar instrutor", description = "Deletar instrutor pelo id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Instrutor deletado"),
        @ApiResponse(responseCode = "404", description = "Instrutor não encontrado")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        instrutorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
