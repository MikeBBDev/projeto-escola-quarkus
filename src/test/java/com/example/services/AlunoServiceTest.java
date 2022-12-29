package com.example.services;

import com.example.dtoRequests.AlunoRequest;
import com.example.dtoResponses.AlunoResponse;
import com.example.dtoResponses.TutorResponse;
import com.example.mapper.AlunoMapper;
import com.example.models.Aluno;
import com.example.models.Professor;
import com.example.repository.AlunoRepository;
import com.example.repository.ProfessorRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class AlunoServiceTest {

    private static final int ID = 300;
    private AlunoMapper mapper = Mockito.mock(AlunoMapper.class);
    private AlunoRepository repository = Mockito.mock(AlunoRepository.class);
    private ProfessorRepository professorRepository = Mockito.mock(ProfessorRepository.class);

    private Aluno entity1 = Mockito.mock(Aluno.class);
    private Aluno entity2 = Mockito.mock(Aluno.class);

    private AlunoResponse response1 = Mockito.mock(AlunoResponse.class);
    private AlunoResponse response2 = Mockito.mock(AlunoResponse.class);

    private AlunoService service = new AlunoService(mapper, repository, professorRepository);

    @Test
    void testRetrieveAll() {
        // GIVEN
        var listOfResponses = List.of(response1, response2);
        var listOfEntities = List.of(entity1, entity2);
        given(repository.listAll())
                .willReturn(listOfEntities);
        given(mapper.toResponse(listOfEntities))
                .willReturn(listOfResponses);

        // WHEN
        var actual = service.retrieveAll();

        // THEN
        assertThat(actual).isEqualTo(listOfResponses);
    }

    @Test
    void testSave() {
        // GIVEN
        AlunoRequest request = Mockito.mock(AlunoRequest.class);

        var entity = Aluno.builder()
                .name(request.getName())
                .build();

        given(mapper.toResponse(entity))
                .willReturn(response1);

        // WHEN
        var actual = service.save(request);

        // THEN
        assertThat(actual).isEqualTo(response1);
        verify(repository, times(1)).persist(entity);
    }

    @Test
    void testGetById() {
        // GIVEN
        given(repository.findById(ID))
                .willReturn(entity1);
        given(mapper.toResponse(entity1))
                .willReturn(response1);

        // WHEN
        var actual = service.getById(ID);

        // THEN
        assertThat(actual).isEqualTo(response1);
    }

    @Test
    void testGetByIdNotFound() {
        // GIVEN
        // WHEN
        // THEN
        assertThatThrownBy(() -> service.getById(ID))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Aluno não encontrado");
    }

    @Test
    void testUpdateTutor() {
        // GIVEN
        var idProfessor = 500;
        var professor = Mockito.mock(Professor.class);
        var expected = Mockito.mock(TutorResponse.class);

        given(repository.findById(ID))
                .willReturn(entity1);
        given(professorRepository.findById(idProfessor))
                .willReturn(professor);
        given(mapper.toResponse(professor))
                .willReturn(expected);

        // WHEN
        var actual = service.updateTutor(ID, idProfessor);

        // THEN
        assertThat(actual).isEqualTo(expected);
        verify(repository, times(1)).persist(entity1);
    }

    @Test
    void testUpdateTutorAlunoNotFound() {
        // GIVEN
        // WHEN
        // THEN
        assertThatThrownBy(() -> service.updateTutor(ID, 100))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Aluno não encontrado");

        verify(repository, never()).persist(any(Aluno.class));
    }

    @Test
    void testUpdateTutorProfessorNotFound() {
        // GIVEN
        given(repository.findById(ID))
                .willReturn(entity1);

        // WHEN
        // THEN
        assertThatThrownBy(() -> service.updateTutor(ID, 100))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Professor não encontrado");
        verify(repository, never()).persist(any(Aluno.class));
    }

    @Test
    void testGetTutoradosByProfessorId() {
        // GIVEN
        var idProfessor = 500;
        var professor = Mockito.mock(Professor.class);
        var listOfEntities = List.of(entity1, entity2);
        var listOfResponses = List.of(response1, response2);

        given(professorRepository.findById(idProfessor))
                .willReturn(professor);
        given(repository.getTutoradosByProfessor(professor))
                .willReturn(listOfEntities);
        given(mapper.toResponse(listOfEntities))
                .willReturn(listOfResponses);

        // WHEN
        var actual = service.getTutoradosByProfessorId(idProfessor);

        // THEN
        assertThat(actual).isEqualTo(listOfResponses);
    }

    @Test
    void testGetTutoradosByProfessorIdProfessorNotFound() {
        // GIVEN
        // WHEN
        // THEN
        assertThatThrownBy(() -> service.getTutoradosByProfessorId(ID))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Professor não encontrado");
        verify(repository, never()).getTutoradosByProfessor(any(Professor.class));
    }

}