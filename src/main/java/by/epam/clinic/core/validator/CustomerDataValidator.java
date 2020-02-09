package by.epam.clinic.core.validator;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class CustomerDataValidator{
    private static final String NAME_PATTERN = "[A-Za-zА-Яа-я]{2,20}";

    private static final String PHONE_PATTERN = "\\+\\d{12}";

    public static boolean isDataValid(String name, String surname, String lastname,
                                      String birthday, String phone) {
        if(name != null && surname != null && lastname != null && birthday != null
        && phone != null) {
            try {
                LocalDate birthdayDate = LocalDate.parse(birthday);
                if(birthdayDate.isAfter(LocalDate.of(1930,1,1)) &&
                        birthdayDate.isBefore(LocalDate.of(2010,1,1))) {
                    return name.matches(NAME_PATTERN) && surname.matches(NAME_PATTERN) && lastname.matches(NAME_PATTERN)
                            && phone.matches(PHONE_PATTERN);
                } else return false;
            } catch (DateTimeParseException exception) {
                return false;
            }
        } else {
            return false;
        }
    }
}
