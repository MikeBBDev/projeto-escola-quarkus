package com.example.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import com.example.dtoRequests.DisciplinaRequest;
import com.example.dtoResponses.DisciplinaResponse;
import com.example.models.Disciplina;

@ApplicationScoped
public class DisciplinaMapper {
    public List<DisciplinaResponse> toResponse(List<Disciplina> listOfDisciplinas) {

        if (Objects.isNull(listOfDisciplinas))
            return new ArrayList<>();

        return listOfDisciplinas.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public DisciplinaResponse toResponse(Disciplina entity) {

        // if (Objects.isNull(entity))
        //     return null;

        Objects.requireNonNull(entity, "Entidade não deve ser nula");

        return DisciplinaResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public Disciplina toEntity(DisciplinaRequest request) {
        if (Objects.isNull(request)) {
            return null;
        } else {
            return Disciplina.builder()
                    .name(request.getName())
                    .build();
        }
    }
}