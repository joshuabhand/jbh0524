import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test the {@link Inventory} class
 */
public class InventoryTest {
    static Inventory inventory;
    static ToolType toolTypeLadder;
    static ToolType toolTypeChainsaw;
    static ToolType toolTypeJackhammer;

    @BeforeAll
    public static void setup() {
        inventory = Inventory.getInstance();

        toolTypeLadder = new ToolType(ToolType.TOOL_NAME_LADDER, 1.99f, true, true, false);
        toolTypeChainsaw = new ToolType(ToolType.TOOL_NAME_CHAINSAW, 1.49f, true, false, true);
        toolTypeJackhammer = new ToolType(ToolType.TOOL_NAME_JACKHAMMER, 2.99f, true, false, false);

        inventory.addHoliday(LocalDate.of(2024, 7, 4));
    }

    /**
     * Test if holidays are added successfully
     */
    @Test
    public void testAddHoliday() {
        LocalDate date = LocalDate.of(2024, 5, 3);
        String dateKey = DateHelper.getHolidayKey(date);
        inventory.addHoliday(date);
        assertNotNull(inventory.holidayMap.get(dateKey));
    }

    /**
     * Test if week days are counted. Mon, May 6 - Thur, May 9
     */
    @Test
    public void testChargeDaysOnWeekdays() {
        LocalDate checkoutDate = LocalDate.of(2024, 5, 6);
        LocalDate dueDate = checkoutDate.plusDays(3);
        int chargeDays = inventory.getChargeDays(checkoutDate, dueDate, toolTypeLadder);
        assertEquals(3, chargeDays);
    }

    /**
     * Test if weekends are counted. Fri, May 3 - Mon, May 6
     */
    @Test
    public void testChargeDaysOnWeekends() {
        LocalDate checkoutDate = LocalDate.of(2024, 5, 3);
        LocalDate dueDate = checkoutDate.plusDays(3);
        int chargeDays = inventory.getChargeDays(checkoutDate, dueDate, toolTypeLadder);
        assertEquals(3, chargeDays);
    }

    /**
     * Test if weekends are NOT counted. Fri, May 3 - Mon, May 6
     */
    @Test
    public void testChargeDaysNotOnWeekends() {
        LocalDate checkoutDate = LocalDate.of(2024, 5, 3);
        LocalDate dueDate = checkoutDate.plusDays(3);
        int chargeDays = inventory.getChargeDays(checkoutDate, dueDate, toolTypeJackhammer);
        assertEquals(1, chargeDays);
    }

    /**
     * Test if holidays are counted. Wed, July 3 - Fri, July 5
     */
    @Test
    public void testChargeDaysOnHolidays() {
        LocalDate checkoutDate = LocalDate.of(2024, 7, 3);
        LocalDate dueDate = checkoutDate.plusDays(2);
        int chargeDays = inventory.getChargeDays(checkoutDate, dueDate, toolTypeChainsaw);
        assertEquals(2, chargeDays);
    }

    /**
     * Test if holidays are NOT counted. Wed, July 3 - Fri, July 5
     */
    @Test
    public void testChargeDaysNotOnHolidays() {
        LocalDate checkoutDate = LocalDate.of(2024, 7, 3);
        LocalDate dueDate = checkoutDate.plusDays(2);
        int chargeDays = inventory.getChargeDays(checkoutDate, dueDate, toolTypeLadder);
        assertEquals(1, chargeDays);
    }

    /**
     * Test if checkout returns correctly formatted rental agreement
     */
    @Test
    public void testCheckout() {
        LocalDate checkoutDate = LocalDate.of(2024, 5, 3);
        Tool toolLadder = new Tool(Tool.TOOL_CODE_LADW, toolTypeLadder, Tool.TOOL_BRAND_WERNER);
        inventory.addTool(toolLadder);
        String printout = inventory.checkout(Tool.TOOL_CODE_LADW, 5, 20, checkoutDate);
        String expectedResult =
                "Tool code: LADW\n" +
                "Tool type: Ladder\n" +
                "Tool brand: Werner\n" +
                "Rental days: 5\n" +
                "Check out date: 05/03/24\n" +
                "Due date: 05/08/24\n" +
                "Daily rental charge: $1.99\n" +
                "Charge days: 5\n" +
                "Pre-discount charge: $9.95\n" +
                "Discount percent: 20%\n" +
                "Discount amount: $1.99\n" +
                "Final charge: $7.96";
        assertEquals(expectedResult, printout);
    }

    /**
     * Test if checkout throws an exception for an invalid rental day count
     */
    @Test
    public void testCheckoutWithInvalidRentalDayCount() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            inventory.checkout(Tool.TOOL_CODE_CHNS, 0, 20, null);
        });

        String expectedMessage = "Rental day count must be 1 or greater";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test if checkout throws an exception for an invalid discount percentage
     */
    @Test
    public void testCheckoutWithInvalidDiscount() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            inventory.checkout(Tool.TOOL_CODE_CHNS, 1, 120, null);
        });

        String expectedMessage = "Discount percent must be in the range of 0 - 100";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}
