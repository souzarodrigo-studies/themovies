package themovies.helpers.log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.MDC;

import themovies.helpers.models.RequestScopeID;


@Provider
@Log
public class LogFilter implements ContainerRequestFilter, ContainerResponseFilter {

    @Inject
    Logger logger;

    @Inject
    RequestScopeID requestScopeID;

    @Override
    public void filter(ContainerRequestContext request) throws IOException {
        MDC.put("requestID", requestScopeID.toString());

        Map<String, String> infoLog = new HashMap<>();
        infoLog.put("path", request.getUriInfo().getPath());
        infoLog.put("method", request.getMethod());
        logger.info("Requisição {}", infoLog);
    }

    @Override
    public void filter(ContainerRequestContext request, ContainerResponseContext response) throws IOException {
        MDC.put("requestID", requestScopeID.toString());

        Map<String, String> infoLog = new HashMap<>();
        infoLog.put("path", request.getUriInfo().getPath());
        infoLog.put("method", request.getMethod());
        infoLog.put("statusCode", String.valueOf(response.getStatus()));
        logger.info("Resposta {}", infoLog);
    }
}