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

import com.example.dtoRequests.DisciplinaRequest;
import com.example.services.DisciplinaService;

@Path("/disciplinas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DisciplinaResource {
    private final DisciplinaService service;

    @Inject
    public DisciplinaResource(DisciplinaService service) {
        this.service = service;
    }

    @GET
    public Response listDisciplina() {
        final var response = service.retrieveAll();
        return Response.ok(response).build();
    }

    @GET
    @Path("/{id}")
    public Response getDisciplina(@PathParam("id") int id) {
        final var response = service.getById(id);
        return Response.ok(response).build();
    }

    @POST
    public Response saveDisciplina(final DisciplinaRequest disciplina) {
        final var response = service.save(disciplina);
        return Response
                .status(Response.Status.CREATED)
                .entity(response)
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response updateDisciplina(@PathParam("id") int id, DisciplinaRequest disciplina) {
        var response = service.update(id, disciplina);
        return Response
                .ok(response)
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response removeDisciplina(@PathParam("id") int id) {
        service.delete(id);
        return Response
                .status(Response.Status.NO_CONTENT)
                .build();
    }
}