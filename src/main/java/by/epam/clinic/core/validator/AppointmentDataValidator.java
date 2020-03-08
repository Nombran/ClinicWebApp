package by.epam.clinic.core.validator;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AppointmentDataValidator {

    private static final LocalTime START_OF_WORKING_DAY = LocalTime.of(7,59);

    private static final LocalTime END_OF_WORKING_DAY = LocalTime.of(22,0);

    private static final String PURPOSE_REGEX = "[а-яА-Я0-9.,\\s]{10,60}";

    public static boolean isTimeValid(LocalDateTime dateTime) {
        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
        LocalTime time = dateTime.toLocalTime();
        LocalDateTime futureTime = LocalDateTime.now();
        futureTime = futureTime.minusHours(1);
        return time.isAfter(START_OF_WORKING_DAY) && time.isBefore(END_OF_WORKING_DAY)
                && dateTime.isAfter(futureTime) && dayOfWeek != DayOfWeek.SUNDAY &&
                dayOfWeek != DayOfWeek.SATURDAY;
    }

    public static boolean isPurposeValid(String purpose) {
        return purpose != null && purpose.matches(PURPOSE_REGEX);
    }
}
