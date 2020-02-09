package by.epam.clinic.core.model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public final class LocalDateTimeConverter {
    public static long toMilliseconds(LocalDateTime dateTime) {
        ZonedDateTime zonedDateTime = dateTime.atZone(ZoneId.systemDefault());
        Instant instant = zonedDateTime.toInstant();
        return instant.toEpochMilli();
    }
}
