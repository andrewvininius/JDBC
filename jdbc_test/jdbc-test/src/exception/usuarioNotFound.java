package exception;

public class usuarioNotFound extends RuntimeException {
    public usuarioNotFound(String message) {
        super(message);
    }
}
