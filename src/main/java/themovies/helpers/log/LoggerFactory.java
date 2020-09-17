package themovies.helpers.log;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.slf4j.Logger;

public class LoggerFactory {
    @Produces
    public Logger createLogger(InjectionPoint ip) {
        return org.slf4j.LoggerFactory.getLogger(ip.getMember().getDeclaringClass());
    }
}
