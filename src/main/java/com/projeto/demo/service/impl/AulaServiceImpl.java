package com.projeto.demo.service.impl;

import static java.util.Optional.ofNullable;

import org.springframework.stereotype.Service;

import com.projeto.demo.domain.model.Aula;
import com.projeto.demo.repository.AulaRepository;
import com.projeto.demo.service.AulaService;
import com.projeto.demo.service.exception.BusinessException;

import jakarta.transaction.Transactional;

@Service
public class AulaServiceImpl extends CrudServiceImpl<Aula, Long> implements AulaService {

    public AulaServiceImpl(AulaRepository aulaRepository) {
        super(aulaRepository);
    }

    @Override
    @Transactional
    public Aula create(Aula aulaToCreate) {
        ofNullable(aulaToCreate)
                .orElseThrow(() -> new BusinessException("Aula a ser criada não pode ser nula."));
        ofNullable(aulaToCreate.getDescricao())
                .orElseThrow(() -> new BusinessException("Descrição da aula não pode ser nula."));
        ofNullable(aulaToCreate.getDataHora())
                .orElseThrow(() -> new BusinessException("Data e hora da aula não podem ser nulas."));
        ofNullable(aulaToCreate.getInstrutor())
                .orElseThrow(() -> new BusinessException("Instrutor da aula não pode ser nulo."));
        ofNullable(aulaToCreate.getEstilo())
                .orElseThrow(() -> new BusinessException("Estilo da aula não pode ser nulo."));
        if (aulaToCreate.getAlunos() == null) {
            throw new BusinessException("Lista de alunos não pode ser nula.");
        }
        return super.create(aulaToCreate);
    }

    @Override
    @Transactional
    public Aula update(Long id, Aula aulaToUpdate) {
        if (!id.equals(aulaToUpdate.getId())) {
            throw new BusinessException("IDs incompatíveis para atualização.");
        }
        Aula dbAula = super.findById(id);
        dbAula.setDescricao(aulaToUpdate.getDescricao());
        dbAula.setDataHora(aulaToUpdate.getDataHora());
        dbAula.setInstrutor(aulaToUpdate.getInstrutor());
        dbAula.setEstilo(aulaToUpdate.getEstilo());
        dbAula.setAlunos(aulaToUpdate.getAlunos());
        return super.create(dbAula);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        super.delete(id);
    }
}
