package by.epam.clinic.util;

public class TextEncryptorException extends Exception {
    public TextEncryptorException() {
    }

    public TextEncryptorException(String message) {
        super(message);
    }

    public TextEncryptorException(String message, Throwable cause) {
        super(message, cause);
    }

    public TextEncryptorException(Throwable cause) {
        super(cause);
    }
}
