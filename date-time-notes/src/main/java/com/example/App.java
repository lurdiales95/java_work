package com.example;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class App {
    public static void main(String[] args) {
        dateOnly();
    }

    public static void dateOnly() {
        LocalDate localNow = LocalDate.now();
        System.out.println(localNow);

        LocalDate custom = LocalDate.of(2025, 6, 27);
    }

    public static void bridgingTime() {
        // get the local system date time, without time zone
        LocalDateTime localNow = LocalDateTime.now();
        System.out.println(localNow);

        // convert to time zone system date time
        ZonedDateTime localWithZone = localNow.atZone(ZoneId.systemDefault());

        // convert to utc date time
        ZonedDateTime utcNow = localWithZone.withZoneSameInstant(ZoneOffset.UTC);
        System.out.println(utcNow);


    }

    // UTC (Coordinated Universal Time)
    public static void utcTimeSnippets() {
        Instant now = Instant.now();
        System.out.println(now);

        // epoch
        Instant fromEpoch = Instant.ofEpochSecond(1719504600);
        System.out.println(fromEpoch);

        Instant now2 = Instant.now();
        ZonedDateTime utcTime = now2.atZone(ZoneOffset.UTC);
        System.out.println(utcTime);
    }

    public static void localTimeSnippets() {
        LocalTime current = LocalTime.now(); // system time
        int hour = 12;
        int minute = 15;
        int second = 35;
        LocalTime noon = LocalTime.of(hour, minute, second);

        LocalTime parsed = LocalTime.parse("12:15:35");

        LocalTime customFormat = LocalTime.parse("2:30 PM",
                DateTimeFormatter.ofPattern("h:mm a")); // look it up or ask LLM

        LocalTime sample = LocalTime.of(8, 30);
        LocalTime twoHoursLater = sample.plusHours(4); // 12:30 pm

        LocalTime twoHoursBefore = sample.minusHours(2); // 6:30 am

        // comparing times
        boolean isBefore = sample.isBefore(twoHoursLater); // false
        boolean isAfter = sample.isAfter(twoHoursBefore); // true

        if (sample.isAfter(twoHoursBefore)) {
            System.out.printf("%s is after %s", sample, twoHoursBefore);
        }
    }

    public static boolean isBusinessHours(LocalTime time) {
        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(17, 0);

        return !time.isBefore(start) && !time.isAfter(end);
    }

    public static boolean isTimeAvailable(LocalTime proposed) {
        LocalTime unavailableStart = LocalTime.of(9, 0);
        LocalTime unavailableEnd = LocalTime.of(17, 0);

//        boolean isAvailable = proposed.isBefore(unavailableStart) || proposed.isAfter(unavailableEnd);
//        return isAvailable;
        return proposed.isBefore(unavailableStart) || proposed.isAfter(unavailableEnd);
    }
}