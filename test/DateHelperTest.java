import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test the {@link DateHelper} class
 */
public class DateHelperTest {
    private static final LocalDate holiday = LocalDate.of(2024, 7, 4);

    /**
     * Test if a holiday is added successfully
     */
    @Test
    public void testAddHoliday() {
        DateHelper dateHelper = new DateHelper();
        dateHelper.addHoliday(holiday);

        assertTrue(dateHelper.isHoliday(holiday));
    }

    /**
     * Test if holidays are added successfully
     */
    @Test
    public void testAddHolidaysForYear() {
        DateHelper dateHelper = new DateHelper();
        dateHelper.addHolidaysForYear(2024);

        assertTrue(dateHelper.isHoliday(holiday));
    }

    /**
     * Test if labor day dates are correct
     */
    @Test
    public void testGetLaborDay() {
        LocalDate date = DateHelper.getLaborDay(2024);
        assertEquals(2, date.getDayOfMonth());

        LocalDate date2 = DateHelper.getLaborDay(2020);
        assertEquals(7, date2.getDayOfMonth());
    }

    /**
     * Test if independence day dates are correct
     */
    @Test
    public void testGetObservedIndependenceDay() {
        LocalDate saturdayDate = DateHelper.getObservedIndependenceDay(2020);
        assertEquals("Friday", saturdayDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US));

        LocalDate sundayDate = DateHelper.getObservedIndependenceDay(2021);
        assertEquals("Monday", sundayDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US));

        LocalDate thursdayDate = DateHelper.getObservedIndependenceDay(2024);
        assertEquals("Thursday", thursdayDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US));
    }

    /**
     * Test if holiday key is correct format
     */
    @Test
    public void testGetHolidayKey() {
        String holidayKey = DateHelper.getHolidayKey(LocalDate.of(2024, 7, 4));
        String expected = "74";
        assertEquals(expected, holidayKey);
    }
}
