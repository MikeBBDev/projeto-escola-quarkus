package com.example.services;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

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
        log.info("Listing alunos");
        final List<Disciplina> listOfDisciplina = repository.listAll();
        return mapper.toResponse(listOfDisciplina);
    }

    public DisciplinaResponse getById(int id) {
        log.info("Getting aluno id-{}", id);

        Disciplina disciplina = repository.findById(id);
        return mapper.toResponse(disciplina);
    }

    @Transactional
    public DisciplinaResponse save(DisciplinaRequest disciplinaRequest) {

        log.info("Saving disciplina - {}", disciplinaRequest);

        Disciplina entity = Disciplina.builder()
                .name(disciplinaRequest.getName())
                .build();

        repository.persistAndFlush(entity);

        return mapper.toResponse(entity);
    }

    @Transactional
    public DisciplinaResponse update(int id, DisciplinaRequest disciplinaRequest) {

        log.info("Updating disciplina id - {}, data - {}", id, disciplinaRequest);

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
        log.info("Deleting disciplina id - {}", id);
        Optional<Disciplina> disciplina = repository.findByIdOptional(id);
        disciplina.ifPresent(repository::delete);
    }
}