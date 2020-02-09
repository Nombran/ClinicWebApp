package by.epam.clinic.core.pool;

public class TransactionManagerException extends Exception{
    public TransactionManagerException() {
    }

    public TransactionManagerException(String message) {
        super(message);
    }

    public TransactionManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionManagerException(Throwable cause) {
        super(cause);
    }
}
