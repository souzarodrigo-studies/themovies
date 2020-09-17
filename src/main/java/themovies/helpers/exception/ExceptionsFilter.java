package themovies.helpers.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import themovies.helpers.error.ListaErro;
import themovies.helpers.models.EnumMensagens;
import themovies.helpers.models.Erro;

@Provider
public class ExceptionsFilter implements ExceptionMapper<Exception> {

    protected static final String LINHA = " - linha: ";
    protected static final String PONTO = ".";
    protected static final String PACKAGE_NAME = "themovies";

    private static final Logger LOG = LoggerFactory.getLogger(ExceptionsFilter.class);

    @Override
    public Response toResponse(Exception e) {
        LOG.error("Error with exception log", e);

        String source = "";
        String motivo = e.getMessage();

        for (StackTraceElement stackTraceElement : e.getStackTrace()) {
            // Pega a primeiro ocorrencia elemento da lista de stackTrace caso nao encontre
            // dentro do stack
            if (source.length() == 0) {
                source = stackTraceElement.getClassName() + LINHA + stackTraceElement.getLineNumber() + PONTO;
            }
            // Pega a primeira ocorrencia de erro dentro do codigo do projeto pelo package
            // name
            if (stackTraceElement.getClassName().contains(PACKAGE_NAME)) {
                source = stackTraceElement.getClassName() + LINHA + stackTraceElement.getLineNumber() + PONTO;
                break;
            }
        }

        Erro erro = new Erro.Builder(EnumMensagens.ERRO_APLICACAO, source).withFormatedDeveloperMessage(motivo).build();

        return Response.status(500).type(MediaType.APPLICATION_JSON_TYPE).entity(new ListaErro(erro)).build();
    }
}
