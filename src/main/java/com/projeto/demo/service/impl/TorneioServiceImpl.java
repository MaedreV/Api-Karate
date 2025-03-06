package com.projeto.demo.service.impl;

import static java.util.Optional.ofNullable;

import org.springframework.stereotype.Service;

import com.projeto.demo.domain.model.Torneio;
import com.projeto.demo.repository.TorneioRepository;
import com.projeto.demo.service.TorneioService;
import com.projeto.demo.service.exception.BusinessException;

import jakarta.transaction.Transactional;

@Service
public class TorneioServiceImpl extends CrudServiceImpl<Torneio, Long> implements TorneioService {

    public TorneioServiceImpl(TorneioRepository torneioRepository) {
        super(torneioRepository);
    }

    @Override
    @Transactional
    public Torneio create(Torneio torneioToCreate) {
        ofNullable(torneioToCreate)
                .orElseThrow(() -> new BusinessException("Torneio a ser criado não pode ser nulo."));
        ofNullable(torneioToCreate.getNome())
                .orElseThrow(() -> new BusinessException("Nome do torneio não pode ser nulo."));
        ofNullable(torneioToCreate.getDataEvento())
                .orElseThrow(() -> new BusinessException("Data do torneio não pode ser nula."));
        if (torneioToCreate.getParticipantes() == null) {
            throw new BusinessException("Lista de participantes não pode ser nula.");
        }
        return super.create(torneioToCreate);
    }

    @Override
    @Transactional
    public Torneio update(Long id, Torneio torneioToUpdate) {
        if (!id.equals(torneioToUpdate.getId())) {
            throw new BusinessException("IDs incompatíveis para atualização.");
        }
        Torneio dbTorneio = super.findById(id);
        dbTorneio.setNome(torneioToUpdate.getNome());
        dbTorneio.setDataEvento(torneioToUpdate.getDataEvento());
        dbTorneio.setEstilo(torneioToUpdate.getEstilo());
        dbTorneio.setParticipantes(torneioToUpdate.getParticipantes());
        return super.create(dbTorneio);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        super.delete(id);
    }
}
