package themovies.helpers.metric;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import themovies.helpers.error.ErroNegocialException;
import themovies.helpers.error.ListaErro;


@Provider
public class ExceptionsErroNegocialFilter implements ExceptionMapper<ErroNegocialException> {

    private static final Logger LOG = LoggerFactory.getLogger(ExceptionsErroNegocialFilter.class);

    @Override
    public Response toResponse(ErroNegocialException erroNegocial) {
        LOG.error("Error with exception log", erroNegocial);

        return Response.status(422).type(MediaType.APPLICATION_JSON_TYPE).entity(new ListaErro(erroNegocial.getErro()))
                .build();
    }

}
