package com.example.resources;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.example.dtoRequests.AlunoRequest;
import com.example.services.AlunoService;

@Path("/alunos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AlunoResource {
    private final AlunoService service;

    @Inject
    public AlunoResource(AlunoService service) {
        this.service = service;
    }

    @GET
    public Response listAlunos() {
        final var response = service.retrieveAll();
        return Response.ok(response).build();
    }

    @GET
    @Path("/{id}")
    public Response getAluno(@PathParam("id") int id) {
        final var response = service.getById(id);
        return Response.ok(response).build();
    }

    @POST
    public Response saveAluno(final AlunoRequest aluno) {
        final var response = service.save(aluno);
        return Response
                .status(Response.Status.CREATED)
                .entity(response)
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response updateAluno(@PathParam("id") int id, AlunoRequest aluno) {
        var response = service.update(id, aluno);
        return Response
                .ok(response)
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response removeAluno(@PathParam("id") int id) {
        service.delete(id);
        return Response
                .status(Response.Status.NO_CONTENT)
                .build();
    }
}