package by.epam.clinic.core.pool;

public class ConnectionProviderException extends Exception {
    public ConnectionProviderException() {
    }

    public ConnectionProviderException(String message) {
        super(message);
    }

    public ConnectionProviderException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionProviderException(Throwable cause) {
        super(cause);
    }
}
