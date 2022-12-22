package com.example.repository;

import javax.enterprise.context.ApplicationScoped;

import com.example.models.Aluno;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class AlunoRepository implements PanacheRepositoryBase<Aluno, Integer> {
}