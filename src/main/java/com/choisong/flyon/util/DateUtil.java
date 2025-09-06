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
        LocalDateTime now = LocalDateTime.now(SEOUL);

        int hour = now.getHour();
        LocalDateTime base;

        if (hour < 6) {
            // 오늘 06시 발표 전이면 어제 18시를 써야 함
            base = now.minusDays(1).withHour(18).withMinute(0).withSecond(0).withNano(0);
        } else if (hour < 18) {
            // 오늘 06시 발표 이후, 18시 발표 전 → 오늘 06시
            base = now.withHour(6).withMinute(0).withSecond(0).withNano(0);
        } else {
            // 오늘 18시 이후 → 오늘 18시
            base = now.withHour(18).withMinute(0).withSecond(0).withNano(0);
        }

        return base.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
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
