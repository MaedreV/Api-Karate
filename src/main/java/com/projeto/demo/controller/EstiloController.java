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

import com.projeto.demo.controller.dto.EstiloDto;
import com.projeto.demo.service.EstiloService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin
@RestController
@RequestMapping("/estilos")
@Tag(name = "Estilos Controller", description = "RESTful API for estilos.")
public record EstiloController(EstiloService estiloService) {

    @GetMapping
    @Operation(summary = "Recuperar todos estilos", description = "Recuperar uma lista com todos estilos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucesso")
    })
    public ResponseEntity<List<EstiloDto>> findAll() {
        var estilos = estiloService.findAll();
        var estilosDto = estilos.stream().map(EstiloDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(estilosDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Recuperar um estilo", description = "Recuperar um estilo usando o id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucesso"),
        @ApiResponse(responseCode = "404", description = "Estilo não encontrado")
    })
    public ResponseEntity<EstiloDto> findById(@PathVariable Long id) {
        var estilo = estiloService.findById(id);
        return ResponseEntity.ok(new EstiloDto(estilo));
    }

    @PostMapping
    @Operation(summary = "Criar novo  estilo", description = "Criar e retornar novo estilo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Estilo criado "),
        @ApiResponse(responseCode = "422", description = "Dados de estilo invalido")
    })
    public ResponseEntity<EstiloDto> create(@RequestBody EstiloDto estiloDto) {
        var estilo = estiloService.create(estiloDto.toModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(estilo.getId())
                .toUri();
        return ResponseEntity.created(location).body(new EstiloDto(estilo));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um estilo", description = "Atualizar um estilo pelo id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estilo atualizado"),
        @ApiResponse(responseCode = "404", description = "Estilo não encontrado"),
        @ApiResponse(responseCode = "422", description = "Dados de estilo invalido")
    })
    public ResponseEntity<EstiloDto> update(@PathVariable Long id, @RequestBody EstiloDto estiloDto) {
        var estilo = estiloService.update(id, estiloDto.toModel());
        return ResponseEntity.ok(new EstiloDto(estilo));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um estilo", description = "Deletar estilo pelo id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Estilo deletado"),
        @ApiResponse(responseCode = "404", description = "Estilo não encontrado")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        estiloService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
