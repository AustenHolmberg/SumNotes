package com.example.sumnotes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
// java.time.* imports are no longer needed for this method

public class DateConverter {
    public static String formatTimestamp(long timestamp) {
        SimpleDateFormat formatter = new SimpleDateFormat("MMM d yyyy", Locale.US);
        Date date = new Date(timestamp);
        return formatter.format(date);
    }
}