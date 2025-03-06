package com.projeto.demo.service.impl;

import static java.util.Optional.ofNullable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projeto.demo.domain.model.Aluno;
import com.projeto.demo.repository.AlunoRepository;
import com.projeto.demo.service.AlunoService;
import com.projeto.demo.service.exception.BusinessException;

@Service
public class AlunoServiceImpl extends CrudServiceImpl<Aluno, Long> implements AlunoService {

    public AlunoServiceImpl(AlunoRepository alunoRepository) {
        super(alunoRepository);
    }

    @Override
    @Transactional
    public Aluno create(Aluno alunoToCreate) {
        ofNullable(alunoToCreate)
                .orElseThrow(() -> new BusinessException("Aluno a ser criado não pode ser nulo."));
        ofNullable(alunoToCreate.getNome())
                .orElseThrow(() -> new BusinessException("Nome do aluno não pode ser nulo."));
        ofNullable(alunoToCreate.getFaixa())
                .orElseThrow(() -> new BusinessException("Faixa do aluno não pode ser nula."));
        ofNullable(alunoToCreate.getDataIngresso())
                .orElseThrow(() -> new BusinessException("Data de ingresso do aluno não pode ser nula."));
        return super.create(alunoToCreate);
    }

    @Override
    @Transactional
    public Aluno update(Long id, Aluno alunoToUpdate) {
        if (!id.equals(alunoToUpdate.getId())) {
            throw new BusinessException("IDs incompatíveis para atualização.");
        }
        Aluno dbAluno = super.findById(id);
        dbAluno.setNome(alunoToUpdate.getNome());
        dbAluno.setFaixa(alunoToUpdate.getFaixa());
        dbAluno.setDataIngresso(alunoToUpdate.getDataIngresso());
        return super.create(dbAluno);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // Aqui você pode adicionar regras específicas para deleção, se necessário.
        super.delete(id);
    }
}
