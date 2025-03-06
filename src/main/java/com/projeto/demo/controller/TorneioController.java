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

import com.projeto.demo.controller.dto.TorneioDto;
import com.projeto.demo.service.TorneioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin
@RestController
@RequestMapping("/torneios")
@Tag(name = "Torneios Controller", description = "RESTful API for torneios.")
public record TorneioController(TorneioService torneioService) {

    @GetMapping
    @Operation(summary = "Recuperar torneios", description = "Recuperar uma lista de torneios")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucesso")
    })
    public ResponseEntity<List<TorneioDto>> findAll() {
        var torneios = torneioService.findAll();
        var torneiosDto = torneios.stream().map(TorneioDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(torneiosDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Recuperar torneio", description = "Recuperar torneio pelo id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucesso"),
        @ApiResponse(responseCode = "404", description = "Torneio não encontrado")
    })
    public ResponseEntity<TorneioDto> findById(@PathVariable Long id) {
        var torneio = torneioService.findById(id);
        return ResponseEntity.ok(new TorneioDto(torneio));
    }

    @PostMapping
    @Operation(summary = "Criar novo torneio", description = "Criar e retornar novo torneio")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Torneio criado"),
        @ApiResponse(responseCode = "422", description = "Dados de torneio invalido")
    })
    public ResponseEntity<TorneioDto> create(@RequestBody TorneioDto torneioDto) {
        var torneio = torneioService.create(torneioDto.toModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(torneio.getId())
                .toUri();
        return ResponseEntity.created(location).body(new TorneioDto(torneio));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um torneio", description = "Atualizar torneio pelo id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Torneio atualizado"),
        @ApiResponse(responseCode = "404", description = "Torneio não encontrado"),
        @ApiResponse(responseCode = "422", description = "Dados de torneio invalido")
    })
    public ResponseEntity<TorneioDto> update(@PathVariable Long id, @RequestBody TorneioDto torneioDto) {
        var torneio = torneioService.update(id, torneioDto.toModel());
        return ResponseEntity.ok(new TorneioDto(torneio));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um torneio", description = "Deletar um torneio pelo id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Torneio deletado"),
        @ApiResponse(responseCode = "404", description = "Torneio não encontrado")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        torneioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
