package by.epam.clinic.core.validator;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class CustomerDataValidator{
    private static final String NAME_PATTERN = "[A-Za-zА-Яа-я]{2,20}";

    private static final String PHONE_PATTERN = "\\+\\d{12}";

    private static final LocalDate LOWER_BOUND_OF_TIME = LocalDate.of(1930,1,1);

    private static final LocalDate UPPER_BOUND_OF_TIME = LocalDate.of(2010,1,1);

    public static boolean isDataValid(String name, String surname, String lastname,
                                      String birthday, String phone) {
        if(name != null && surname != null && lastname != null && birthday != null
        && phone != null) {
            try {
                LocalDate birthdayDate = LocalDate.parse(birthday);
                if(birthdayDate.isAfter(LOWER_BOUND_OF_TIME) &&
                        birthdayDate.isBefore(UPPER_BOUND_OF_TIME)) {
                    return name.matches(NAME_PATTERN)
                            && surname.matches(NAME_PATTERN)
                            && lastname.matches(NAME_PATTERN)
                            && phone.matches(PHONE_PATTERN);
                }
            } catch (DateTimeParseException exception) {
                return false;
            }
        }
        return false;
    }

    private CustomerDataValidator() {}
}
