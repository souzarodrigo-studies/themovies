package themovies.helpers.exception;

import javax.servlet.ServletException;

public class ResourceAPIException extends ServletException {

    /**
     *
     */
    private static final long serialVersionUID = -3910635420069880008L;

    public ResourceAPIException(String message) {
        super(message);
    }
}
