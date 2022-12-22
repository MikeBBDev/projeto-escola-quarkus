package com.example.mapper;

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

        // if (Objects.isNull(entity))
        //     return null;

        Objects.requireNonNull(entity, "Entidade não deve ser nula");

        return ProfessorResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
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