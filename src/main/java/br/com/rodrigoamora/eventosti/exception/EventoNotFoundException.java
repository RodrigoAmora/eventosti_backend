package br.com.rodrigoamora.eventosti.exception;

public class EventoNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 5861310537366287163L;

    public EventoNotFoundException() {
        super();
    }

    public EventoNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public EventoNotFoundException(final String message) {
        super(message);
    }

    public EventoNotFoundException(final Throwable cause) {
        super(cause);
    }

}
