package com.projeto.demo.service.impl;

import static java.util.Optional.ofNullable;

import org.springframework.stereotype.Service;

import com.projeto.demo.domain.model.Estilo;
import com.projeto.demo.repository.EstiloRepository;
import com.projeto.demo.service.EstiloService;
import com.projeto.demo.service.exception.BusinessException;

import jakarta.transaction.Transactional;

@Service
public class EstiloServiceImpl extends CrudServiceImpl<Estilo, Long> implements EstiloService {

    public EstiloServiceImpl(EstiloRepository estiloRepository) {
        super(estiloRepository);
    }

    @Override
    @Transactional
    public Estilo create(Estilo estiloToCreate) {
        ofNullable(estiloToCreate)
                .orElseThrow(() -> new BusinessException("Estilo a ser criado não pode ser nulo."));
        ofNullable(estiloToCreate.getNome())
                .orElseThrow(() -> new BusinessException("Nome do estilo não pode ser nulo."));
        ofNullable(estiloToCreate.getDescricao())
                .orElseThrow(() -> new BusinessException("Descrição do estilo não pode ser nula."));
        return super.create(estiloToCreate);
    }

    @Override
    @Transactional
    public Estilo update(Long id, Estilo estiloToUpdate) {
        if (!id.equals(estiloToUpdate.getId())) {
            throw new BusinessException("IDs incompatíveis para atualização.");
        }
        Estilo dbEstilo = super.findById(id);
        dbEstilo.setNome(estiloToUpdate.getNome());
        dbEstilo.setDescricao(estiloToUpdate.getDescricao());
        return super.create(dbEstilo);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        super.delete(id);
    }
}
