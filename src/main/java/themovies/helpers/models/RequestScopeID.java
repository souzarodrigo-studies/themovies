package themovies.helpers.models;

import java.util.UUID;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class RequestScopeID {

    private final UUID requestID;

    public RequestScopeID() {
        this.requestID = UUID.randomUUID();
    }

    public UUID getRequestID() {
        return requestID;
    }

    @Override
    public String toString() {
        return requestID.toString();
    }
}
