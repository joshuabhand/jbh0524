import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateHelper {
    private static final Calendar cacheCalendar = new GregorianCalendar();

    //getFirstMondayOfSeptember
    public static LocalDate getLaborDay(int year) {
        cacheCalendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cacheCalendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, 1);
        cacheCalendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
        cacheCalendar.set(Calendar.YEAR, year);

//        return cacheCalendar.get(Calendar.DAY_OF_MONTH);
        return LocalDate.of(year, 9, cacheCalendar.get(Calendar.DAY_OF_MONTH));
    }

    public static LocalDate getObservedIndependenceDay(int year) {
        LocalDate observedDate = LocalDate.of(year, 7, 4);

        if("Saturday".equalsIgnoreCase(observedDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US))) {
            observedDate = observedDate.minusDays(1);
        }
        else if("Sunday".equalsIgnoreCase(observedDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US))) {
            observedDate = observedDate.plusDays(1);
        }

//        System.out.println("test: " + observedDate.getDayOfWeek().getValue());
//        System.out.println("test: " + observedDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US));

        return observedDate;
    }

    public static String getHolidayKey(LocalDate holiday) {
        return String.valueOf(holiday.getMonthValue()) + holiday.getDayOfMonth();
    }
}
