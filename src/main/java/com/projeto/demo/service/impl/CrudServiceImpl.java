package com.projeto.demo.service.impl;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.projeto.demo.service.CrudService;
import com.projeto.demo.service.exception.NotFoundException;

public class CrudServiceImpl<T, ID> implements CrudService<T, ID> {

    protected final JpaRepository<T, ID> repository;

    public CrudServiceImpl(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public T findById(ID id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Entidade não encontrada com o ID: " + id));
    }

    @Override
    @Transactional
    public T create(T entity) {
        return repository.save(entity);
    }

    @Override
    @Transactional
    public T update(ID id, T entity) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Entidade não encontrada com o ID: " + id);
        }
        return repository.save(entity);
    }

    @Override
    @Transactional
    public void delete(ID id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Entidade não encontrada com o ID: " + id);
        }
        repository.deleteById(id);
    }
}
