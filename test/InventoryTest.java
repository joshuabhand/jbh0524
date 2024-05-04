import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest {
    Inventory inventory;

    @BeforeEach
    public void setup() {
         inventory = Inventory.getInstance();
    }

    @AfterEach
    public void tearDown() {
        inventory.holidayMap.clear();
        inventory.toolsMap.clear();
    }

    @Test
    public void testAddHoliday() {
        LocalDate date = LocalDate.of(2024, 5, 3);
        String dateKey = DateHelper.getHolidayKey(date);
        inventory.addHoliday(date);
        assertNotNull(inventory.holidayMap.get(dateKey));
    }

    @Test
    public void testChargeDaysOnWeekdays() {
        LocalDate checkoutDate = LocalDate.of(2024, 5, 6);
        LocalDate dueDate = checkoutDate.plusDays(3);
        ToolType toolTypeLadder = new ToolType("Ladder", 1, true, true, false);
        Tool toolLadder = new Tool("LADW", toolTypeLadder, "Werner");
        inventory.addTool(toolLadder);
        int chargeDays = inventory.getChargeDays(checkoutDate, dueDate, toolTypeLadder);
        assertEquals(3, chargeDays);
    }

    @Test
    public void testChargeDaysOnWeekends() {
        LocalDate checkoutDate = LocalDate.of(2024, 5, 3);
        LocalDate dueDate = checkoutDate.plusDays(3);
        ToolType toolTypeLadder = new ToolType("Ladder", 1, true, true, false);
        Tool toolLadder = new Tool("LADW", toolTypeLadder, "Werner");
        inventory.addTool(toolLadder);
        int chargeDays = inventory.getChargeDays(checkoutDate, dueDate, toolTypeLadder);
        assertEquals(3, chargeDays);
    }

    @Test
    public void testChargeDaysNotOnWeekends() {
        LocalDate checkoutDate = LocalDate.of(2024, 5, 3);
        LocalDate dueDate = checkoutDate.plusDays(3);
        ToolType toolTypeLadder = new ToolType("Jackhammer", 1, true, false, false);
        Tool toolLadder = new Tool("JAKR", toolTypeLadder, "Ridgid");
        inventory.addTool(toolLadder);
        int chargeDays = inventory.getChargeDays(checkoutDate, dueDate, toolTypeLadder);
        assertEquals(1, chargeDays);
    }

    @Test
    public void testChargeDaysOnHolidays() {
        LocalDate checkoutDate = LocalDate.of(2024, 7, 3);
        LocalDate dueDate = checkoutDate.plusDays(2);
        ToolType toolTypeLadder = new ToolType("Chainsaw", 1, false, false, true);
        Tool toolLadder = new Tool("CHNS", toolTypeLadder, "Stihl");
        inventory.addTool(toolLadder);
        inventory.addHoliday(LocalDate.of(2024, 7, 4));
        int chargeDays = inventory.getChargeDays(checkoutDate, dueDate, toolTypeLadder);
        assertEquals(1, chargeDays);
    }

    @Test
    public void testChargeDaysNotOnHolidays() {
        LocalDate checkoutDate = LocalDate.of(2024, 7, 3);
        LocalDate dueDate = checkoutDate.plusDays(2);
        ToolType toolTypeLadder = new ToolType("Chainsaw", 1, true, false, false);
        Tool toolLadder = new Tool("CHNS", toolTypeLadder, "Stihl");
        inventory.addTool(toolLadder);
        inventory.addHoliday(LocalDate.of(2024, 7, 4));
        int chargeDays = inventory.getChargeDays(checkoutDate, dueDate, toolTypeLadder);
        assertEquals(1, chargeDays);
    }

    @Test
    public void testCheckout() {
        LocalDate checkoutDate = LocalDate.of(2024, 7, 3);
        ToolType toolTypeLadder = new ToolType("Chainsaw", 1, true, false, false);
        Tool toolLadder = new Tool("CHNS", toolTypeLadder, "Stihl");
        inventory.addTool(toolLadder);
        String printout = inventory.checkout("CHNS", 3, 20, checkoutDate);
        assertNotNull(printout);
    }

    @Test
    public void testCheckoutWithInvalidRentalDayCount() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            inventory.checkout("CHNS", 0, 20, null);
        });

        String expectedMessage = "Rental day count must be 1 or greater";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testCheckoutWithInvalidDiscount() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            inventory.checkout("CHNS", 1, 120, null);
        });

        String expectedMessage = "Discount percent must be in the range of 0 - 100";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}
