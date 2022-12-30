package com.example.resources;

import com.example.dtoRequests.DisciplinaRequest;
import com.example.dtoResponses.DisciplinaResponse;
import com.example.mapper.DisciplinaMapper;
import com.example.models.Disciplina;
import com.example.repository.DisciplinaRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class DisciplinaResourceTest {

    private static final String NAME = "some name";

    @Inject
    DisciplinaRepository repository;

    @Inject
    DisciplinaMapper mapper;

    @BeforeEach
    @Transactional
    void setup() {
        repository.deleteAll();
    }

    @Transactional
    void saveToDisciplinaRepository(Disciplina... entities) {
        for (Disciplina entity : entities) {
            repository.persist(entity);
        }
    }

    @Test
    @DisplayName("List disciplinas")
    void testList() {
        // GIVEN
        var entityOne = Disciplina.builder().name("Química").build();
        var entityTwo = Disciplina.builder().name("Matemática").build();

        saveToDisciplinaRepository(entityOne, entityTwo);

        // WHEN
        var response = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/disciplinas")
                .then()
                .extract()
                .response();

        var dto = response.getBody().as(DisciplinaResponse[].class);

        // THEN
        assertThat(response.getStatusCode())
                .isEqualTo(Response.Status.OK.getStatusCode());

        assertThat(repository.count()).isEqualTo(2);

        assertThat(dto.length).isEqualTo(2);

        assertThat(mapper.toResponse(repository.findById(dto[0].getId())))
                .isEqualTo(dto[0]);

        assertThat(mapper.toResponse(repository.findById(dto[1].getId())))
                .isEqualTo(dto[1]);
    }

    @Test
    @DisplayName("Should create a new disciplina")
    void testSave() {
        // GIVEN
        // WHEN
        var request = DisciplinaRequest.builder()
                .name(NAME)
                .build();

        var response = given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/disciplinas")
                .then()
                .extract()
                .response();

        var dto = response.getBody().as(DisciplinaResponse.class);

        // THEN
        assertThat(response.getStatusCode())
                .isEqualTo(Response.Status.CREATED.getStatusCode());

        assertThat(repository.count()).isEqualTo(1);

        assertThat(mapper.toResponse(repository.findById(dto.getId())))
                .isEqualTo(dto);
    }

    @Test
    @DisplayName("Should not create a new disciplina")
    void testSaveInvalidRequest() {
        // GIVEN
        // WHEN
        var request = DisciplinaRequest.builder()
                .name(" ")
                .build();

        var response = given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/disciplinas")
                .then()
                .extract()
                .response();

        // THEN
        assertThat(response.getStatusCode())
                .isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());

        assertThat(repository.count()).isEqualTo(0);
    }

    @Test
    @DisplayName("Get disciplina by id")
    void testGetById() {
        //GIVEN
        var entityOne = Disciplina.builder().name("Informática").build();
        var entityTwo = Disciplina.builder().name("Física").build();

        saveToDisciplinaRepository(entityOne, entityTwo);

        //WHEN
        var response =
                given()
                .when()
                    .get("/disciplinas/" + entityOne.getId())
                        .then()
                        .extract()
                        .response();

        var dto = response.getBody().as(DisciplinaResponse.class);

        //THEN
        assertThat(response.getStatusCode())
                .isEqualTo(Response.Status.OK.getStatusCode());

        assertThat(mapper.toResponse(repository.findById(dto.getId())))
                .isEqualTo(dto);
    }
}
