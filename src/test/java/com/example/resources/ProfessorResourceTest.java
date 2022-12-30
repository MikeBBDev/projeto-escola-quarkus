package com.example.resources;

import com.example.dtoRequests.ProfessorRequest;
import com.example.dtoResponses.ProfessorResponse;
import com.example.mapper.ProfessorMapper;
import com.example.models.Professor;
import com.example.repository.ProfessorRepository;
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
public class ProfessorResourceTest {

    private static final String NAME = "some name";

    @Inject
    ProfessorRepository repository;

    @Inject
    ProfessorMapper mapper;

    @BeforeEach
    @Transactional
    void setup() {
        repository.deleteAll();
    }

    @Transactional
    void saveToProfessorRepository(Professor... entities) {
        for (Professor entity : entities) {
            repository.persist(entity);
        }
    }

    @Test
    @DisplayName("List Professors")
    void testList() {
        // GIVEN
        var entityOne = Professor.builder().name("João").build();
        var entityTwo = Professor.builder().name("Juliano").build();

        saveToProfessorRepository(entityOne, entityTwo);

        // WHEN
        var response = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/professores")
                .then()
                .extract()
                .response();

        var dto = response.getBody().as(ProfessorResponse[].class);

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
    @DisplayName("Should create a new Professor")
    void testSave() {
        // GIVEN
        // WHEN
        var request = ProfessorRequest.builder()
                .name(NAME)
                .build();

        var response = given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/professores")
                .then()
                .extract()
                .response();

        var dto = response.getBody().as(ProfessorResponse.class);

        // THEN
        assertThat(response.getStatusCode())
                .isEqualTo(Response.Status.CREATED.getStatusCode());

        assertThat(repository.count()).isEqualTo(1);

        assertThat(mapper.toResponse(repository.findById(dto.getId())))
                .isEqualTo(dto);
    }

    @Test
    @DisplayName("Should not create a new Professor")
    void testSaveInvalidRequest() {
        // GIVEN
        // WHEN
        var request = ProfessorRequest.builder()
                .name(" ")
                .build();

        var response = given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/professores")
                .then()
                .extract()
                .response();

        // THEN
        assertThat(response.getStatusCode())
                .isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());

        assertThat(repository.count()).isEqualTo(0);
    }

    @Test
    @DisplayName("Get Professor by id")
    void testGetById() {
        //GIVEN
        var entityOne = Professor.builder().name("Nuno").build();
        var entityTwo = Professor.builder().name("Thiago").build();

        saveToProfessorRepository(entityOne, entityTwo);

        //WHEN
        var response =
                given()
                .when()
                    .get("/professores/" + entityOne.getId())
                        .then()
                        .extract()
                        .response();

        var dto = response.getBody().as(ProfessorResponse.class);

        //THEN
        assertThat(response.getStatusCode())
                .isEqualTo(Response.Status.OK.getStatusCode());

        assertThat(mapper.toResponse(repository.findById(dto.getId())))
                .isEqualTo(dto);
    }
}
