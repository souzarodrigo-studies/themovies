package themovies.resources;

import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import themovies.helpers.exception.ResourceAPIException;
import themovies.models.MovieModel;
import themovies.services.MovieServices;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@RequestScoped
@Path("movies")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MoviesResource {

    @Inject
    private MovieServices service;

    @Context
    UriInfo uriInfo;

    @GET
    @Metered
    @Operation(summary = "Get All Movies", description = "This method list all movies from repository")
    @APIResponse(responseCode = "200", description = "Everything went ok, we found this movies: ", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MovieModel.class)))
    @APIResponse(responseCode = "404", description = "Movie not found")
    public Response listAll() {

        return Response.status(Response.Status.OK).entity(service.listAll()).links(getHateoas("listALL", "GET"))
                .build();

    }

    @GET
    @Metered
    @Path("{id}")
    @Operation(summary = "Get Movie By Code", description = "This method get movie from repository by id")
    @APIResponse(responseCode = "200", description = "Everything went ok, we get a movie", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MovieModel.class)))
    @APIResponse(responseCode = "404", description = "Movie not found")
    public Response findById(@PathParam("id") Long id) {

        try {
            MovieModel entity = service.findById(id);
            return Response.status(Response.Status.OK).entity(entity).links(getHateoas("findById", "GET")).build();
        } catch (ResourceAPIException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage())
                    .links(getHateoas("findById", "GET")).build();
        }

    }

    @POST
    @Metered
    @Operation(summary = "Save Movie", description = "This method Save movie from body of request")
    @APIResponse(responseCode = "200", description = "Everything went ok, we save your movie ")
    @APIResponse(responseCode = "409", description = "Movie already exist")
    public Response saveMovie(@Valid MovieModel entity) {

        /// * Mark - Valida se tem algum titulo ja existente
        if (service.validByName(entity)) {
            return Response.status(Response.Status.CONFLICT).entity("Movie_Already_Exist")
                    .links(getHateoas("save", "POST")).build();
        } else {
            service.save(entity);
            return Response.status(Response.Status.CREATED).links(getHateoas("save", "POST")).build();
        }
    }

    @PUT
    @Metered
    @Path("{id}")
    @Operation(summary = "Update Movie", description = "This method update movie from data of body request")
    @APIResponse(responseCode = "200", description = "Everything went ok, we update your movie ")
    @APIResponse(responseCode = "404", description = "not found movie on repository")
    public Response update(@PathParam("id") Long id, @Valid MovieModel movie) {

        try {
            return Response.status(Response.Status.OK).entity(service.update(movie)).links(getHateoas("update", "PUT"))
                    .build();
        } catch (ResourceAPIException e) {
            return Response.status(Response.Status.NOT_FOUND).links(getHateoas("update", "PUT")).build();
        }
    }

    @DELETE
    @Metered
    @Path("{id}")
    @Operation(summary = "Save Movie", description = "This method delete movie by id")
    @APIResponse(responseCode = "204", description = "Everything went ok, we delete your movie ")
    @APIResponse(responseCode = "404", description = "We not found your movie in repository")
    public Response deleteMovie(@PathParam("id") Long id) {

        try {
            service.delete(id);
            return Response.status(Response.Status.NO_CONTENT).links(getHateoas("deleteMovies", "DELETE")).build();
        } catch (ResourceAPIException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage())
                    .links(getHateoas("deleteMovies", "DELETE")).build();
        }
    }

    public Link getHateoas(String methodName, String httpVerb) {
        return Link.fromUri(uriInfo.getAbsolutePath()).rel(methodName).type(httpVerb).build();
    }

}
