package com.example.repository;

import javax.enterprise.context.ApplicationScoped;

import com.example.models.Disciplina;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class DisciplinaRepository implements PanacheRepositoryBase<Disciplina, Integer> {
}