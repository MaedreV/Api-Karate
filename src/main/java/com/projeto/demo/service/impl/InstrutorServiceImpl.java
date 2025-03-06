package com.projeto.demo.service.impl;

import static java.util.Optional.ofNullable;

import org.springframework.stereotype.Service;

import com.projeto.demo.domain.model.Instrutor;
import com.projeto.demo.repository.InstrutorRepository;
import com.projeto.demo.service.InstrutorService;
import com.projeto.demo.service.exception.BusinessException;

import jakarta.transaction.Transactional;

@Service
public class InstrutorServiceImpl extends CrudServiceImpl<Instrutor, Long> implements InstrutorService {

    public InstrutorServiceImpl(InstrutorRepository instrutorRepository) {
        super(instrutorRepository);
    }

    @Override
    @Transactional
    public Instrutor create(Instrutor instrutorToCreate) {
        ofNullable(instrutorToCreate)
                .orElseThrow(() -> new BusinessException("Instrutor a ser criado não pode ser nulo."));
        ofNullable(instrutorToCreate.getNome())
                .orElseThrow(() -> new BusinessException("Nome do instrutor não pode ser nulo."));
        ofNullable(instrutorToCreate.getEstilo())
                .orElseThrow(() -> new BusinessException("Estilo do instrutor não pode ser nulo."));
        return super.create(instrutorToCreate);
    }

    @Override
    @Transactional
    public Instrutor update(Long id, Instrutor instrutorToUpdate) {
        if (!id.equals(instrutorToUpdate.getId())) {
            throw new BusinessException("IDs incompatíveis para atualização.");
        }
        Instrutor dbInstrutor = super.findById(id);
        dbInstrutor.setNome(instrutorToUpdate.getNome());
        dbInstrutor.setEstilo(instrutorToUpdate.getEstilo());
        return super.create(dbInstrutor);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        super.delete(id);
    }
}
