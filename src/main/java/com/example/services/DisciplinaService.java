package com.example.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;

import com.example.dtoRequests.DisciplinaRequest;
import com.example.dtoResponses.DisciplinaResponse;
import com.example.dtoResponses.TitularResponse;
import com.example.mapper.DisciplinaMapper;
import com.example.models.Disciplina;
import com.example.repository.DisciplinaRepository;
import com.example.repository.ProfessorRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
@RequiredArgsConstructor
public class DisciplinaService {

    private final DisciplinaMapper mapper;
    private final DisciplinaRepository repository;
    private final ProfessorRepository professorRepository;

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
        Objects.requireNonNull(disciplinaRequest, "Requisições não devem ser nulas");

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

    @Transactional
    public TitularResponse updateTitular(int idDisciplina, int idProfessor) {

        log.info("Atualizando titular: disciplina-id: {}, professor-id: {}", idDisciplina, idProfessor);

        var disciplina = repository.findById(idDisciplina);
        var professor = professorRepository.findById(idProfessor);

        if (Objects.isNull(disciplina)) throw new EntityNotFoundException("Disciplina não encontrada");
        if (Objects.isNull(professor)) throw new EntityNotFoundException("Professor não encontrado");

        var query = repository.find("titular", professor);
        if (query.count() > 0) throw new IllegalStateException("Professor deve possuir somente uma disciplina como Titular");

        disciplina.setTitular(professor);
        repository.persist(disciplina);

        return mapper.toResponse(professor);
    }

    public DisciplinaResponse getDisciplinaByProfessorId(int idProfessor) {

        log.info("Mostrando a disciplina pelo id do professor: {}", idProfessor);

        var professor = professorRepository.findById(idProfessor);
        if (Objects.isNull(professor)) throw new EntityNotFoundException("Professor não encontrado");

        var query = repository.find("titular", professor);
        if (query.count() == 0) throw new EntityNotFoundException("Disciplina não encontrada");
        if (query.count() > 1) throw new IllegalStateException("Professor deve possuir somente uma disciplina como Titular");

        var disciplina = query.singleResult();

        return mapper.toResponse(disciplina);

    }

}