package com.example.dtoRequests;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.Validation;
import javax.validation.Validator;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class AlunoRequestTest {

    private static final String NAME = "algum nome";

    @Test
    void testConstructorBuilder() {

        //GIVEN
        final var factory = Validation.buildDefaultValidatorFactory();
        final var validator = factory.getValidator();

        //WHEN
        var request =
                AlunoRequest.builder()
                        .name(NAME)
                        .build();

        //THEN
        assertFields(validator, request);
        factory.close();
    }

    @Test
    void testConstructorAllArgs() {
        //GIVEN
        final var factory = Validation.buildDefaultValidatorFactory();
        final var validator = factory.getValidator();

        //WHEN
        var request = new AlunoRequest(NAME);

        //THEN
        assertFields(validator, request);
        factory.close();
    }

    @Test
    void testConstructorDefault() {
        //GIVEN
        final var factory = Validation.buildDefaultValidatorFactory();
        final var validator = factory.getValidator();

        //WHEN
        var request = new AlunoRequest();
        request.setName(NAME);

        //THEN
        assertFields(validator, request);
        factory.close();
    }

    @ParameterizedTest
    @MethodSource("invalidFields")
    void testConstructor_NotAllowed(final String name, final String errorMessage) {
        //GIVEN
        final var factory = Validation.buildDefaultValidatorFactory();
        final var validator = factory.getValidator();

        //WHEN
        var request = new AlunoRequest();
        request.setName(name);

        //THEN
        final var violations = validator.validate(request);
        assertThat(violations)
                .isNotEmpty()
                .hasSize(1);
        assertThat(violations.stream().findFirst().get().getMessage())
                .isEqualTo(errorMessage);
        factory.close();
    }

    @Test
    void testEqualsAndHashcodeContract() {
        EqualsVerifier.simple().forClass(AlunoRequest.class)
                .verify();
    }

    static Stream<Arguments> invalidFields() {
        return Stream.of(
                arguments(null, "Nome deve ser não nulo"),
                arguments("   ", "Nome deve ser não nulo")
        );
    }

    private void assertFields(final Validator validator, final AlunoRequest request) {
        final var violations = validator.validate(request);
        assertThat(violations).isEmpty();

        assertThat(request.getName()).isEqualTo(NAME);
    }
}