package edu.miu.courseregistrationcore.integration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateFormatter {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LocalDate parseDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use 'yyyy-MM-dd'.", e);
        }
    }
}
