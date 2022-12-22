package com.example.services;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import com.example.dtoRequests.ProfessorRequest;
import com.example.dtoResponses.ProfessorResponse;
import com.example.mapper.ProfessorMapper;
import com.example.models.Professor;
import com.example.repository.ProfessorRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
@RequiredArgsConstructor
public class ProfessorService {

    private final ProfessorMapper mapper;
    private final ProfessorRepository repository;

    public List<ProfessorResponse> retrieveAll() {
        log.info("Listing professors");
        final List<Professor> listOfProfessors = repository.listAll();
        return mapper.toResponse(listOfProfessors);
    }

    public ProfessorResponse getById(int id) {
        log.info("Getting professor id-{}", id);

        Professor professor = repository.findById(id);
        return mapper.toResponse(professor);
    }

    // public ProfessorResponse getBySex(enum sexo) {
    //     log.info("Getting professor sex-{}", sexo);

    //     Professor professor = repository.findBySex(sexo);
    //     return mapper.toResponse(professor);
    // }

    // public ProfessorResponse getByDiscipline(String discplina) {
    //     log.info("Getting professor discipline-{}", disciplina);

    //     Professor professor = repository.findByDiscipline(discplina);
    //     return mapper.toResponse(professor);
    // }

    @Transactional
    public ProfessorResponse save(ProfessorRequest professorRequest) {

        log.info("Saving professor - {}", professorRequest);

        Professor entity = Professor.builder()
                .name(professorRequest.getName())
                .build();

        repository.persistAndFlush(entity);

        return mapper.toResponse(entity);
    }

    @Transactional
    public ProfessorResponse update(int id, ProfessorRequest professorRequest) {

        log.info("Updating professor id - {}, data - {}", id, professorRequest);

        Optional<Professor> professor = repository.findByIdOptional(id);

        if (professor.isPresent()) {
            var entity = professor.get();
            entity.setName(professorRequest.getName());
            return mapper.toResponse(entity);
        }

        return new ProfessorResponse();
    }

    @Transactional
    public void delete(int id) {
        log.info("Deleting professor id - {}", id);
        Optional<Professor> professor = repository.findByIdOptional(id);
        professor.ifPresent(repository::delete);
    }
}