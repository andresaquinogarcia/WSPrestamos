package montepiedad.prestamo.error;

public class TokenInvalidoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TokenInvalidoException(String message) {
        super(message);
    }
}
