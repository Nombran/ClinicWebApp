package by.epam.clinic.core.validator;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class AppointmentDataValidator {

    private static final int PURPOSE_MAX_SIZE = 500;

    private static final LocalTime START_OF_WORKING_DAY = LocalTime.of(7,59);

    private static final LocalTime END_OF_WORKING_DAY = LocalTime.of(22,0);

    public static boolean isDataValid(String doctorId, String customerId, String dateTime,
                                      String purpose) {
        if(doctorId != null && customerId != null && dateTime != null) {
            try
            {
                long longId =  Long.parseLong(doctorId);
                long longCustomerId =  Long.parseLong(customerId);
                LocalDateTime localDateTime = LocalDateTime.parse(dateTime);
                System.out.println(dateTime);
                LocalTime time = localDateTime.toLocalTime();
                if(purpose == null || purpose.length() < PURPOSE_MAX_SIZE) {
                    return time.isAfter(START_OF_WORKING_DAY) && time.isBefore(END_OF_WORKING_DAY);
                } else  {
                    return false;
                }
            } catch (NumberFormatException | DateTimeParseException ex) {
                return false;
            }
        } else {
            return false;
        }
    }
}
