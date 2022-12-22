package com.example.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import com.example.dtoRequests.AlunoRequest;
import com.example.dtoResponses.AlunoResponse;
import com.example.models.Aluno;

@ApplicationScoped
public class AlunoMapper {
    public List<AlunoResponse> toResponse(List<Aluno> listOfAlunos) {

        if (Objects.isNull(listOfAlunos))
            return new ArrayList<>();

        return listOfAlunos.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public AlunoResponse toResponse(Aluno entity) {

        // if (Objects.isNull(entity))
        //     return null;

        Objects.requireNonNull(entity, "Entidade não deve ser nula");

        return AlunoResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public Aluno toEntity(AlunoRequest request) {
        if (Objects.isNull(request)) {
            return null;
        } else {
            return Aluno.builder()
                    .name(request.getName())
                    .build();
        }
    }
}