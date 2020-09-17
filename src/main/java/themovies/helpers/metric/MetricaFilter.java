package themovies.helpers.metric;

import java.util.concurrent.TimeUnit;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import org.eclipse.microprofile.metrics.Metadata;
import org.eclipse.microprofile.metrics.MetricRegistry;
import org.eclipse.microprofile.metrics.MetricType;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.Tag;
import org.eclipse.microprofile.metrics.Timer;

/**
 * Classe de filtro para request e response que inclui a metricas padrões do BB.
 * Essa classe esta com escopo de Aplicação e por isso tenha atenção ao incluir
 * novas propriedades pois a mesma será compartilhada com todas as requisições.
 */
@Provider
@Metrica
@ApplicationScoped
public class MetricaFilter implements ContainerRequestFilter, ContainerResponseFilter {
    @Inject
    private MetricRegistry registry;

    private static final String METRIC_HTTP_REQUESTS_COUNTER_NAME = "http_requests_counter";
    private static final String METRIC_HTTP_RESPONSE_COUNTER_NAME = "http_response_counter";
    private static final String METRIC_HTTP_TIMER_NAME = "http_requests_seconds_summary";
    private static final String TIMER_INIT_TIME_MILLISECONDS = "http_init_timer_request_milliseconds";

    private static final String PATH = "path";
    private static final String STATUS = "status";
    private static final String METHOD = "method";

    @Override
    public void filter(ContainerRequestContext request) {
        Tag pathTag = new Tag(PATH, request.getUriInfo().getPath());
        Tag methodTag = new Tag(METHOD, request.getMethod());

        registry.counter(METRIC_HTTP_REQUESTS_COUNTER_NAME, pathTag, methodTag).inc();
        request.setProperty(TIMER_INIT_TIME_MILLISECONDS, System.currentTimeMillis());
    }

    @Override
    public void filter(ContainerRequestContext request, ContainerResponseContext response) {
        Tag pathTag = new Tag(PATH, request.getUriInfo().getPath());
        Tag methodTag = new Tag(METHOD, request.getMethod());
        Tag statusTag = new Tag(STATUS, String.valueOf(response.getStatus()));

        Long initialTimeMilliseconds = Long.parseLong(request.getProperty(TIMER_INIT_TIME_MILLISECONDS).toString());

        Long finalTimeMilliseconds = System.currentTimeMillis() - initialTimeMilliseconds;

        Metadata metadataTimer = Metadata.builder().withName(METRIC_HTTP_TIMER_NAME).withUnit(MetricUnits.SECONDS)
                .withType(MetricType.TIMER).build();

        Timer timerSuccessful = registry.timer(metadataTimer, pathTag, methodTag);

        registry.counter(METRIC_HTTP_RESPONSE_COUNTER_NAME, pathTag, statusTag, methodTag).inc();
        timerSuccessful.update(finalTimeMilliseconds, TimeUnit.MILLISECONDS);
    }
}