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

import com.projeto.demo.controller.dto.AlunoDto;
import com.projeto.demo.service.AlunoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin
@RestController
@RequestMapping("/alunos")
@Tag(name = "Alunos Controller", description = "RESTful API for alunos.")
public record AlunoController(AlunoService alunoService) {

    @GetMapping
    @Operation(summary = "Recuperar todos alunos", description = "Recuperar uma lista de todos os alunos registrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucesso")
    })
    public ResponseEntity<List<AlunoDto>> findAll() {
        var alunos = alunoService.findAll();
        var alunosDto = alunos.stream().map(AlunoDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(alunosDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get aluno pelo ID", description = "Get aluno pelo id dele")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sucesso"),
        @ApiResponse(responseCode = "404", description = "Aluno não encontrado")
    })
    public ResponseEntity<AlunoDto> findById(@PathVariable Long id) {
        var aluno = alunoService.findById(id);
        return ResponseEntity.ok(new AlunoDto(aluno));
    }

    @PostMapping
    @Operation(summary = "Criar novo aluno", description = "Criar e retornar novo aluno")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Aluno criado"),
        @ApiResponse(responseCode = "422", description = "Dados de aluno invalido")
    })
    public ResponseEntity<AlunoDto> create(@RequestBody AlunoDto alunoDto) {
        var aluno = alunoService.create(alunoDto.toModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(aluno.getId())
                .toUri();
        return ResponseEntity.created(location).body(new AlunoDto(aluno));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar aluno", description = "Atualizar aluno pelo id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Aluno atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Aluno não encontrado"),
        @ApiResponse(responseCode = "422", description = "Dados de  aluno invalido")
    })
    public ResponseEntity<AlunoDto> update(@PathVariable Long id, @RequestBody AlunoDto alunoDto) {
        var aluno = alunoService.update(id, alunoDto.toModel());
        return ResponseEntity.ok(new AlunoDto(aluno));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar aluno", description = "Deletar aluno pelo id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Aluno deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Aluno não encontrado")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        alunoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
