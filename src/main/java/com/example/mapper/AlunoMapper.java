package com.example.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import com.example.dtoRequests.AlunoRequest;
import com.example.dtoResponses.AlunoResponse;
import com.example.dtoResponses.TutorResponse;
import com.example.models.Aluno;
import com.example.models.Professor;

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

        Objects.requireNonNull(entity, "Entidade Aluno deve ser não nula");

        var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");

        var response = AlunoResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .dateTime(formatter.format(entity.getDateTime()))
                .build();

        if (Objects.nonNull(entity.getTutor())) {
            response.setTutor(entity.getTutor().getName());
        }

        return response;
    }

    public TutorResponse toResponse(Professor entity) {

        Objects.requireNonNull(entity, "Entidade Tutor deve ser não nula");

        var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");

        return TutorResponse.builder()
                .tutor(entity.getName())
                .atualizacao(formatter.format(LocalDateTime.now()))
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