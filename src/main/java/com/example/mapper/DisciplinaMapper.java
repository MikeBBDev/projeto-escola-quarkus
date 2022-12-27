package com.example.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import com.example.dtoRequests.DisciplinaRequest;
import com.example.dtoResponses.DisciplinaResponse;
import com.example.dtoResponses.TitularResponse;
import com.example.models.Disciplina;
import com.example.models.Professor;

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

        Objects.requireNonNull(entity, "Entidade Disciplina deve ser não nula");

        var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");

        var response = DisciplinaResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .dateTime(formatter.format(entity.getDateTime()))
                .build();

        if (Objects.nonNull(entity.getTitular())) {
            response.setTitular(entity.getTitular().getName());
        }

        return response;
    }

    public TitularResponse toResponse(Professor entity) {

        Objects.requireNonNull(entity, "Entidade Titular deve ser não nula");

        var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");

        return TitularResponse.builder()
                .titular(entity.getName())
                .atualizacao(formatter.format(LocalDateTime.now()))
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