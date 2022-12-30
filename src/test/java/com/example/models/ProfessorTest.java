package com.example.models;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import javax.persistence.Id;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import nl.jqno.equalsverifier.EqualsVerifier;

class ProfessorTest {
    private static final String NAME = "legolas";
    private static final Integer ID = 100;
    private static final Disciplina DISCIPLINA = Disciplina.builder().id(ID).name(NAME).build();
    private static final List<Aluno> TUTORADOS = List.of(Aluno.builder().id(ID).name(NAME).build());
    private static final LocalDateTime DATETIME = LocalDateTime.now().plusDays(-1);

    @Test
    void testConstructorBuilder() {

        // GIVEN
        final var factory = Validation.buildDefaultValidatorFactory();
        final var validator = factory.getValidator();

        // WHEN
        var entity = Professor.builder()
                .id(ID)
                .name(NAME)
                .disciplina(DISCIPLINA)
                .alunos(TUTORADOS)
                .dateTime(DATETIME)
                .build();

        // THEN
        assertFields(validator, entity);
        factory.close();
    }

    @Test
    void testConstructorAllArgs() {
        // GIVEN
        final var factory = Validation.buildDefaultValidatorFactory();
        final var validator = factory.getValidator();

        // WHEN
        var entity = new Professor(ID, NAME, DISCIPLINA, TUTORADOS, DATETIME);

        // THEN - O teste pega aqui por conta do "Nome não deve ser vazio" - devo ter esquecido de colocar nome em algum canto que ainda não achei
        assertFields(validator, entity);
        factory.close();
    }

    @Test
    void testConstructorDefault() {
        // GIVEN
        final var factory = Validation.buildDefaultValidatorFactory();
        final var validator = factory.getValidator();

        // WHEN
        var entity = new Professor();
        entity.setId(ID);
        entity.setName(NAME);
        entity.setDisciplina(DISCIPLINA);
        entity.setAlunos(TUTORADOS);
        entity.setDateTime(DATETIME);

        // THEN
        assertFields(validator, entity);
        factory.close();
    }

    @Test
    void testPrePersist() {
        // GIVEN
        var entity = new Professor(ID, NAME, DISCIPLINA, TUTORADOS, DATETIME);

        // WHEN
        entity.prePersist();

        // THEN
        assertThat(entity.getDateTime()).isNotNull();
    }

    @ParameterizedTest
    @MethodSource("invalidFields")
    void testConstructor_NotAllowed(final String name, final String errorMessage) {
        // GIVEN
        final var factory = Validation.buildDefaultValidatorFactory();
        final var validator = factory.getValidator();

        // WHEN
        var entity = new Professor();
        entity.setId(ID);
        entity.setName(name);
        entity.setDisciplina(DISCIPLINA);
        entity.setAlunos(TUTORADOS);
        entity.setDateTime(DATETIME);

        // THEN
        final var violations = validator.validate(entity);
        assertThat(violations)
                .isNotEmpty()
                .hasSize(1);
        assertThat(violations.stream().findFirst().get().getMessage())
                .isEqualTo(errorMessage);
        factory.close();
    }

    @Test
    void testEqualsAndHashcodeContract() {
        EqualsVerifier.simple().forClass(Professor.class)
                .withIgnoredAnnotations(Id.class)
                .withPrefabValues(Professor.class,
                        Professor.builder().name("prof1").build(), new Professor())
                .verify();
    }

    static Stream<Arguments> invalidFields() {
        return Stream.of(
                arguments(null, "Nome não deve ser vazio"),
                arguments("   ", "Nome não deve ser vazio"));
    }

    private void assertFields(final Validator validator, final Professor entity) {
        final var violations = validator.validate(entity);
        assertThat(violations).isEmpty();

        assertThat(entity.getName()).isEqualTo(NAME);
    }
}