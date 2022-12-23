package com.example.services;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.validation.Valid;

import com.example.dtoRequests.DisciplinaRequest;
import com.example.dtoResponses.DisciplinaResponse;
import com.example.mapper.DisciplinaMapper;
import com.example.models.Disciplina;
import com.example.repository.DisciplinaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
@RequiredArgsConstructor
public class DisciplinaService {

    private final DisciplinaMapper mapper;
    private final DisciplinaRepository repository;

    public List<DisciplinaResponse> retrieveAll() {
        log.info("Listando disciplinas");
        final List<Disciplina> listOfDisciplina = repository.listAll();
        return mapper.toResponse(listOfDisciplina);
    }

    public DisciplinaResponse getById(int id) {
        log.info("Coletando disciplina cuja id é -{}", id);

        Disciplina disciplina = repository.findById(id);
        return mapper.toResponse(disciplina);
    }

    @Transactional
    public DisciplinaResponse save(@Valid DisciplinaRequest disciplinaRequest) {

        log.info("Salvando disciplina - {}", disciplinaRequest);

        Disciplina entity = Disciplina.builder()
                .name(disciplinaRequest.getName())
                .build();

        repository.persistAndFlush(entity);

        return mapper.toResponse(entity);
    }

    @Transactional
    public DisciplinaResponse update(int id, DisciplinaRequest disciplinaRequest) {

        log.info("Atualizando disciplina cuja id é - {}, data - {}", id, disciplinaRequest);

        Optional<Disciplina> disciplina = repository.findByIdOptional(id);

        if (disciplina.isPresent()) {
            var entity = disciplina.get();
            entity.setName(disciplinaRequest.getName());
            return mapper.toResponse(entity);
        }

        return new DisciplinaResponse();
    }

    @Transactional
    public void delete(int id) {
        log.info("Deletando disciplina cuja id é - {}", id);
        Optional<Disciplina> disciplina = repository.findByIdOptional(id);
        disciplina.ifPresent(repository::delete);
    }
}