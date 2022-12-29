package com.example.repository;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;

import com.example.models.Aluno;
import com.example.models.Professor;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class AlunoRepositoryTest {

    @Inject
    AlunoRepository repository;

    @Inject
    ProfessorRepository professorRepository;

    @Test
    void testGetTutoradosByProfessor() {

        // GIVEN
        var professorOne = Professor.builder().name("Professor X").build();
        var professorTwo = Professor.builder().name("Professor Y").build();
        saveToRepository(professorRepository, professorOne, professorTwo);

        var alunoOne = Aluno.builder().name("Jose").tutor(professorOne).build();
        var alunoTwo = Aluno.builder().name("Maria").tutor(professorTwo).build();
        var alunoThree = Aluno.builder().name("Joao").tutor(professorOne).build();
        saveToRepository(repository, alunoOne, alunoTwo, alunoThree);

        // WHEN
        var actual = repository.getTutoradosByProfessor(professorOne);

        // THEN
        assertThat(actual.size()).isEqualTo(2);
        assertThat(actual.get(0).getName()).isEqualTo("Joao");
        assertThat(actual.get(1).getName()).isEqualTo("Jose");
    }

    @Transactional
    void saveToRepository(PanacheRepositoryBase repository, Object... entities) {
        for (Object entity : entities) {
            repository.persist(entity);
        }
    }

}