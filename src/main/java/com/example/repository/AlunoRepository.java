package com.example.repository;

import java.util.List;
import java.util.Objects;

import javax.enterprise.context.ApplicationScoped;

import com.example.models.Aluno;
import com.example.models.Professor;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;

@ApplicationScoped
public class AlunoRepository implements PanacheRepositoryBase<Aluno, Integer> {
    public List<Aluno> getTutoradosByProfessor(Professor professor) {

        Objects.requireNonNull(professor, "Professor não deve ser nulo");

        var query = find("tutor", Sort.ascending("name"), professor);

        return query.list();
    }
}