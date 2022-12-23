package com.example.resources;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
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

import com.example.dtoRequests.ProfessorRequest;
import com.example.dtoResponses.ErrorResponse;
import com.example.services.ProfessorService;

@Path("/professores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProfessorResource {
    private final ProfessorService service;

    @Inject
    public ProfessorResource(ProfessorService service) {
        this.service = service;
    }

    @GET
    public Response listProfessors() {
        final var response = service.retrieveAll();
        return Response.ok(response).build();
    }

    @GET
    @Path("/{id}")
    public Response getProfessor(@PathParam("id") int id) {
        final var response = service.getById(id);
        return Response.ok(response).build();
    }

    @POST
    public Response saveProfessor(final ProfessorRequest professor) {
        try {
            final var response = service.save(professor);

            return Response
                    .status(Response.Status.CREATED)
                    .entity(response)
                    .build();

        } catch (ConstraintViolationException e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ErrorResponse.createFromValidation(e))
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateProfessor(@PathParam("id") int id, ProfessorRequest professor) {
        var response = service.update(id, professor);
        return Response
                .ok(response)
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response removeProfessor(@PathParam("id") int id) {
        service.delete(id);
        return Response
                .status(Response.Status.NO_CONTENT)
                .build();
    }
}