package com.example.mapper;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import com.example.dtoRequests.ProfessorRequest;
import com.example.dtoResponses.ProfessorResponse;
import com.example.models.Professor;

@ApplicationScoped
public class ProfessorMapper {
    public List<ProfessorResponse> toResponse(List<Professor> listOfProfessors) {

        if (Objects.isNull(listOfProfessors))
            return new ArrayList<>();

        return listOfProfessors.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ProfessorResponse toResponse(Professor entity) {
        Objects.requireNonNull(entity, "Entidade Professor deve ser não nula");

        var formatter = DateTimeFormatter.ofPattern("dd-MM-YYYY hh:mm:ss");
        return ProfessorResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .dateTime(formatter.format(entity.getDateTime()))
                .build();
    }

    public Professor toEntity(ProfessorRequest request) {
        if (Objects.isNull(request)) {
            return null;
        } else {
            return Professor.builder()
                    .name(request.getName())
                    .build();
        }
    }
}