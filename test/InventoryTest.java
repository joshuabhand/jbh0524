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
        ToolType toolTypeLadder = new ToolType(ToolType.TOOL_NAME_LADDER, 1, true, true, false);
        Tool toolLadder = new Tool(Tool.TOOL_CODE_LADDER, toolTypeLadder, Tool.TOOL_BRAND_WERNER);
        inventory.addTool(toolLadder);
        int chargeDays = inventory.getChargeDays(checkoutDate, dueDate, toolTypeLadder);
        assertEquals(3, chargeDays);
    }

    @Test
    public void testChargeDaysOnWeekends() {
        LocalDate checkoutDate = LocalDate.of(2024, 5, 3);
        LocalDate dueDate = checkoutDate.plusDays(3);
        ToolType toolTypeLadder = new ToolType(ToolType.TOOL_NAME_LADDER, 1, true, true, false);
        Tool toolLadder = new Tool(Tool.TOOL_CODE_LADDER, toolTypeLadder, Tool.TOOL_BRAND_WERNER);
        inventory.addTool(toolLadder);
        int chargeDays = inventory.getChargeDays(checkoutDate, dueDate, toolTypeLadder);
        assertEquals(3, chargeDays);
    }

    @Test
    public void testChargeDaysNotOnWeekends() {
        LocalDate checkoutDate = LocalDate.of(2024, 5, 3);
        LocalDate dueDate = checkoutDate.plusDays(3);
        ToolType toolTypeLadder = new ToolType(ToolType.TOOL_NAME_JACKHAMMER, 1, true, false, false);
        Tool toolLadder = new Tool(Tool.TOOL_CODE_JACKHAMMER_R, toolTypeLadder, Tool.TOOL_BRAND_RIDGID);
        inventory.addTool(toolLadder);
        int chargeDays = inventory.getChargeDays(checkoutDate, dueDate, toolTypeLadder);
        assertEquals(1, chargeDays);
    }

    @Test
    public void testChargeDaysOnHolidays() {
        LocalDate checkoutDate = LocalDate.of(2024, 7, 3);
        LocalDate dueDate = checkoutDate.plusDays(2);
        ToolType toolTypeLadder = new ToolType(ToolType.TOOL_NAME_CHAINSAW, 1, false, false, true);
        Tool toolLadder = new Tool(Tool.TOOL_CODE_CHAINSAW, toolTypeLadder, Tool.TOOL_BRAND_STIHL);
        inventory.addTool(toolLadder);
        inventory.addHoliday(LocalDate.of(2024, 7, 4));
        int chargeDays = inventory.getChargeDays(checkoutDate, dueDate, toolTypeLadder);
        assertEquals(1, chargeDays);
    }

    @Test
    public void testChargeDaysNotOnHolidays() {
        LocalDate checkoutDate = LocalDate.of(2024, 7, 3);
        LocalDate dueDate = checkoutDate.plusDays(2);
        ToolType toolTypeLadder = new ToolType(ToolType.TOOL_NAME_CHAINSAW, 1, true, false, false);
        Tool toolLadder = new Tool(Tool.TOOL_CODE_CHAINSAW, toolTypeLadder, Tool.TOOL_BRAND_STIHL);
        inventory.addTool(toolLadder);
        inventory.addHoliday(LocalDate.of(2024, 7, 4));
        int chargeDays = inventory.getChargeDays(checkoutDate, dueDate, toolTypeLadder);
        assertEquals(1, chargeDays);
    }

    @Test
    public void testCheckout() {
        LocalDate checkoutDate = LocalDate.of(2024, 7, 3);
        ToolType toolTypeLadder = new ToolType(ToolType.TOOL_NAME_CHAINSAW, 1, true, false, false);
        Tool toolLadder = new Tool(Tool.TOOL_CODE_CHAINSAW, toolTypeLadder, Tool.TOOL_BRAND_STIHL);
        inventory.addTool(toolLadder);
        String printout = inventory.checkout(Tool.TOOL_CODE_CHAINSAW, 3, 20, checkoutDate);
        assertNotNull(printout);
    }

    @Test
    public void testCheckoutWithInvalidRentalDayCount() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            inventory.checkout(Tool.TOOL_CODE_CHAINSAW, 0, 20, null);
        });

        String expectedMessage = "Rental day count must be 1 or greater";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testCheckoutWithInvalidDiscount() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            inventory.checkout(Tool.TOOL_CODE_CHAINSAW, 1, 120, null);
        });

        String expectedMessage = "Discount percent must be in the range of 0 - 100";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}
