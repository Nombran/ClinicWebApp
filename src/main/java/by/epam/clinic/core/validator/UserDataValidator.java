package by.epam.clinic.core.validator;

public class UserDataValidator {
    private static final String LOGIN_PATTERN = "[a-zA-Z][A-Za-z0-9]{7,15}";

    private static final String PASSWORD_PATTERN = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,15}";

    private static final String EMAIL_REGEX = "[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)";

    public static boolean isDataValid(String login, String password, String email) {
        if(login != null && password != null && email != null) {
            return login.matches(LOGIN_PATTERN) && password.matches(PASSWORD_PATTERN)
                    && email.matches(EMAIL_REGEX);
        } else {
            return false;
        }
    }

    public static boolean isDataValid(String login, String password) {
        if(login != null && password != null ) {
            return login.matches(LOGIN_PATTERN) && password.matches(PASSWORD_PATTERN);
        } else {
            return false;
        }
    }

    private UserDataValidator() {
    }
}
