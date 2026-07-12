package com.example.gymtrack.data;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {

    @TypeConverter
    public static Date fromTimestamp(Long timestamp) {
        if (timestamp == null) {
            return null;
        }

        return new Date(timestamp);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        if (date == null) {
            return null;
        }

        return date.getTime();
    }
}