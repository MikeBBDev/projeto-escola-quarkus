package com.example.dtoResponses;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;

import static org.assertj.core.api.Assertions.assertThat;

class AlunoResponseTest {

    private static final int ID = 100;
    private static final String NAME = "ALGUM NOME";
    private static final String TUTOR = "ALGUM TUTOR";
    private static final String DATETIME = "ALGUMA DATE TIME";


    @Test
    void testConstructorBuilder() {

        //GIVEN
        final var factory = Validation.buildDefaultValidatorFactory();
        final var validator = factory.getValidator();

        //WHEN
        var response =
                AlunoResponse.builder()
                        .id(ID)
                        .name(NAME)
                        .tutor(TUTOR)
                        .dateTime(DATETIME)
                        .build();

        //THEN
        assertFields(validator, response);
        factory.close();
    }

    @Test
    void testConstructorAllArgs() {
        //GIVEN
        final var factory = Validation.buildDefaultValidatorFactory();
        final var validator = factory.getValidator();

        //WHEN
        var response = new AlunoResponse(ID, NAME, TUTOR, DATETIME);

        //THEN
        assertFields(validator, response);
        factory.close();
    }

    @Test
    void testConstructorDefault() {
        //GIVEN
        final var factory = Validation.buildDefaultValidatorFactory();
        final var validator = factory.getValidator();

        //WHEN
        var response = new AlunoResponse();
        response.setId(ID);
        response.setName(NAME);
        response.setTutor(TUTOR);
        response.setDateTime(DATETIME);

        //THEN
        assertFields(validator, response);
        factory.close();
    }

    @Test
    void testEqualsAndHashcodeContract() {
        EqualsVerifier.simple().forClass(AlunoResponse.class)
                .verify();
    }

    private void assertFields(final Validator validator, final AlunoResponse response) {
        final var violations = validator.validate(response);
        assertThat(violations).isEmpty();

        assertThat(response.getName()).isEqualTo(NAME);
    }

}