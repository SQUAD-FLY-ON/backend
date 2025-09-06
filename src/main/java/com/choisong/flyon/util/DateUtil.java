package com.choisong.flyon.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateUtil {

    private static final ZoneId SEOUL = ZoneId.of("Asia/Seoul");
    private static final DateTimeFormatter BASE_HH_FMT = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter HHMM_FMT = DateTimeFormatter.ofPattern("HHmm");


    public static String getCurrentBaseDateHour() {
        return LocalDateTime.now(SEOUL).format(BASE_HH_FMT) + "0600";
    }

    public static long daysFromToday(String targetDate) {
        LocalDate target = LocalDate.parse(targetDate, DateTimeFormatter.BASIC_ISO_DATE);
        LocalDate today = LocalDate.now(SEOUL);
        return ChronoUnit.DAYS.between(today, target);
    }

    public static String getCurrentDate() {
        return LocalDate.now(SEOUL).format(DateTimeFormatter.BASIC_ISO_DATE);
    }

}
