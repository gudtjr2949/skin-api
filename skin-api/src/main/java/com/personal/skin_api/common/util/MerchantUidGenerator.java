package com.personal.skin_api.common.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class MerchantUidGenerator {
    public static String generateMerchantUid() {
        String uniqueString = UUID.randomUUID().toString().replace("-", "");
        String formattedDay = makeFormattedDay();
        return formattedDay +'-'+ uniqueString;
    }

    public static String makeFormattedDay() {
        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDay = today.format(formatter).replace("-", "");
        return formattedDay;
    }
}
