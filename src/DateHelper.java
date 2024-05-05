import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * This class helps {@link Inventory} deal with holiday and date values.
 */
public class DateHelper {

    /**
     * This gets the calendar date for which Labor falls on in a given year.
     * First Monday in September.
     *
     * @param year which year to retrieve the labor day date
     */
    public static LocalDate getLaborDay(int year) {
        Calendar calendar = new GregorianCalendar();

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, 1);
        calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
        calendar.set(Calendar.YEAR, year);

        return LocalDate.of(year, 9, calendar.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * This gets the calendar date for which Independence day falls on in a given year.
     * If it falls on a weekend, it is observed on the closest weekday i.e. Sat - Fri
     * or Sun - Mon.
     *
     * @param year which year to retrieve the 4th of July holiday
     */
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

    /**
     * This method creates the holiday map key using month/day as key i.e. "74" as "July 4"
     *
     * @param holiday the holiday to get the key for
     *
     * @return the holiday key value
     */
    public static String getHolidayKey(LocalDate holiday) {
        return String.valueOf(holiday.getMonthValue()) + holiday.getDayOfMonth();
    }
}
