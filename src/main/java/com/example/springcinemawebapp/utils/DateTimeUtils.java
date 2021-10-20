package com.example.springcinemawebapp.utils;

import java.time.LocalTime;

public class DateTimeUtils {
    public static boolean isTimeOverlapping(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
        return start1.isBefore(end2) && start2.isBefore(end1);
    }
}
