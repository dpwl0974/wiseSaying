package com.back.domain.wiseSaying;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Ut {
    public static String getFormattedDate(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedNow = localDateTime.format(formatter);

        return formattedNow;
    }

}
