package com.example.services;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.validation.Valid;

import com.example.dtoRequests.AlunoRequest;
import com.example.dtoResponses.AlunoResponse;
import com.example.mapper.AlunoMapper;
import com.example.models.Aluno;
import com.example.repository.AlunoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoMapper mapper;
    private final AlunoRepository repository;

    public List<AlunoResponse> retrieveAll() {
        log.info("Listando alunos");
        final List<Aluno> listOfAlunos = repository.listAll();
        return mapper.toResponse(listOfAlunos);
    }

    public AlunoResponse getById(int id) {
        log.info("Coletando alunos por id-{}", id);

        Aluno aluno = repository.findById(id);
        return mapper.toResponse(aluno);
    }

    @Transactional
    public AlunoResponse save(@Valid AlunoRequest alunoRequest) {

        log.info("Salvando aluno - {}", alunoRequest);

        Aluno entity = Aluno.builder()
                .name(alunoRequest.getName())
                .build();

        repository.persistAndFlush(entity);

        return mapper.toResponse(entity);
    }

    @Transactional
    public AlunoResponse update(int id, AlunoRequest alunoRequest) {

        log.info("Atualizando aluno cuja id é - {}, data - {}", id, alunoRequest);

        Optional<Aluno> aluno = repository.findByIdOptional(id);

        if (aluno.isPresent()) {
            var entity = aluno.get();
            entity.setName(alunoRequest.getName());
            return mapper.toResponse(entity);
        }

        return new AlunoResponse();
    }

    @Transactional
    public void delete(int id) {
        log.info("Deletando aluno cuja id é - {}", id);
        Optional<Aluno> aluno = repository.findByIdOptional(id);
        aluno.ifPresent(repository::delete);
    }
}