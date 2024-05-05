import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateHelper {

    public static LocalDate getLaborDay(int year) {
        Calendar calendar = new GregorianCalendar();

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, 1);
        calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
        calendar.set(Calendar.YEAR, year);

        return LocalDate.of(year, 9, calendar.get(Calendar.DAY_OF_MONTH));
    }

    public static LocalDate getObservedIndependenceDay(int year) {
        LocalDate observedDate = LocalDate.of(year, 7, 4);

        if("Saturday".equalsIgnoreCase(observedDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US))) {
            observedDate = observedDate.minusDays(1);
        }
        else if("Sunday".equalsIgnoreCase(observedDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US))) {
            observedDate = observedDate.plusDays(1);
        }

        return observedDate;
    }

    public static String getHolidayKey(LocalDate holiday) {
        return String.valueOf(holiday.getMonthValue()) + holiday.getDayOfMonth();
    }
}
