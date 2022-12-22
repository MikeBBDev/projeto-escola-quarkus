package com.example.repository;

import javax.enterprise.context.ApplicationScoped;

import com.example.models.Professor;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class ProfessorRepository implements PanacheRepositoryBase<Professor, Integer> {
}