package com.example.mapper;

import com.example.dtoResponses.DisciplinaResponse;
import com.example.dtoResponses.TitularResponse;
import com.example.models.Disciplina;
import com.example.models.Professor;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class DisciplinaMapperTest{

    private static final String NAME_1 = "some name 1";
    private static final String NAME_2 = "some name 2";
    private static final Integer ID_1 = 100;
    private static final Integer ID_2 = 200;
    private static final Professor TITULAR_1 = Professor.builder().id(ID_1).name(NAME_1).build();
    private static final Professor TITULAR_2 = Professor.builder().id(ID_2).name(NAME_2).build();
    private static final LocalDateTime DATETIME_1 = LocalDateTime.now().plusDays(-1);
    private static final LocalDateTime DATETIME_2 = LocalDateTime.now().plusDays(1);
    private static final Disciplina ENTITY_1 = new Disciplina(ID_1, NAME_1, TITULAR_1, DATETIME_1);
    private static final Disciplina ENTITY_2 = new Disciplina(ID_2, NAME_2, TITULAR_2, DATETIME_2);

    private static final DisciplinaResponse RESPONSE_1 =
            new DisciplinaResponse(ID_1, NAME_1, TITULAR_1.getName(), DATETIME_1.format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")));

    private static final DisciplinaResponse RESPONSE_2 =
            new DisciplinaResponse(ID_2, NAME_2, TITULAR_2.getName(), DATETIME_2.format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")));

    private final DisciplinaMapper mapper = new DisciplinaMapper();


    @Test
    void testToResponseList() {
        //GIVEN
        var listOfEntities = List.of(ENTITY_1, ENTITY_2);

        //WHEN
        var actual = mapper.toResponse(listOfEntities);

        //THEN
        assertThat(actual).containsExactly(RESPONSE_1, RESPONSE_2);

    }

    @Test
    void testToResponseListNull() {
        //GIVEN

        //WHEN
        var actual = mapper.toResponse((List<Disciplina>) null);

        //THEN
        assertThat(actual).isEmpty();

    }

    @Test
    void testToResponseWithoutTitular() {
        //GIVEN
        var entity = new Disciplina(ID_2, NAME_2, null, DATETIME_2);
        var expected = new DisciplinaResponse(ID_2, NAME_2, null, DATETIME_2.format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")));

        //WHEN
        var actual = mapper.toResponse(entity);

        //THEN
        assertThat(actual).isEqualTo(expected);

    }

    @Test
    void testToResponseTitular() {
        //GIVEN
        var expected = TitularResponse.builder().titular(TITULAR_1.getName()).build();

        //WHEN
        var actual = mapper.toResponse(TITULAR_1);

        //THEN
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("atualizacao")
                .isEqualTo(expected);
    }
}
