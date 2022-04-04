package ulaval.glo2003.api.utils;

import ulaval.glo2003.application.exceptions.InvalidParamException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static Date parseStringToDate(String stringDate) {
        var dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        dateFormatter.setLenient(false);

        Date date;
        try {
            date = dateFormatter.parse(stringDate);
        } catch (ParseException e) {
            throw new InvalidParamException();
        }
        return date;
    }

    public static Period getElapsedTimeSince(Date date) {
        var zone = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Period.between(zone, LocalDate.now());
    }

    public static String parseDateToString(Date date) {
        var dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormatter.format(date);
    }
}
