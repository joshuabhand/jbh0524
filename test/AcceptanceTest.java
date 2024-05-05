import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.text.NumberFormat;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This Test class covers the acceptance criteria provided in the original assignment
 */
public class AcceptanceTest {
    static Inventory inventory;

    @BeforeAll
    public static void setup() {
        inventory = Inventory.getInstance();

        inventory.addHoliday(DateHelper.getObservedIndependenceDay(2015));
        inventory.addHoliday(DateHelper.getLaborDay(2015));
        inventory.addHoliday(DateHelper.getObservedIndependenceDay(2020));
        inventory.addHoliday(DateHelper.getLaborDay(2020));

        ToolType toolTypeLadder = new ToolType(ToolType.TOOL_NAME_LADDER, 1.99f, true, true, false);
        ToolType toolTypeChainsaw = new ToolType(ToolType.TOOL_NAME_CHAINSAW, 1.49f, true, false, true);
        ToolType toolTypeJackhammer = new ToolType(ToolType.TOOL_NAME_JACKHAMMER, 2.99f, true, false, false);

        Tool toolLadder = new Tool(Tool.TOOL_CODE_LADW, toolTypeLadder, Tool.TOOL_BRAND_WERNER);
        Tool toolChainsaw = new Tool(Tool.TOOL_CODE_CHNS, toolTypeChainsaw, Tool.TOOL_BRAND_STIHL);
        Tool toolJackhammer = new Tool(Tool.TOOL_CODE_JAKD, toolTypeJackhammer, Tool.TOOL_BRAND_DEWALT);
        Tool toolJackhammer2 = new Tool(Tool.TOOL_CODE_JAKR, toolTypeJackhammer, Tool.TOOL_BRAND_RIDGID);

        inventory.addTool(toolLadder);
        inventory.addTool(toolChainsaw);
        inventory.addTool(toolJackhammer);
        inventory.addTool(toolJackhammer2);
    }

    @Test
    public void test1() {
        LocalDate checkoutDate = LocalDate.of(2015, 9, 3);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            inventory.checkout(Tool.TOOL_CODE_JAKR, 5, 101, checkoutDate);
        });

        String expectedMessage = "Discount percent must be in the range of 0 - 100";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void test2() {
        int rentalDays = 3;
        int discount = 10;
        LocalDate checkoutDate = LocalDate.of(2020, 7, 2);
        LocalDate dueDate = checkoutDate.plusDays(rentalDays);

        LocalDate independenceDay = inventory.holidayMap.get(DateHelper.getHolidayKey(DateHelper.getObservedIndependenceDay(2020)));
        assertNotNull(independenceDay);

        ToolType toolType = inventory.toolsMap.get(Tool.TOOL_CODE_LADW).getToolType();
        int chargeDays = inventory.getChargeDays(checkoutDate, dueDate, toolType);
        assertEquals(2, chargeDays);

        float dailyCharge = toolType.getDailyCharge();
        assertEquals(1.99f, dailyCharge);

        float preDiscountCharge = chargeDays * dailyCharge;
        assertEquals("$3.98", NumberFormat.getCurrencyInstance().format(preDiscountCharge));

        float discountAmount = ((float) discount / 100) * preDiscountCharge;
        assertEquals("$0.40", NumberFormat.getCurrencyInstance().format(discountAmount));

        float finalAmount = preDiscountCharge - discountAmount;
        assertEquals("$3.58", NumberFormat.getCurrencyInstance().format(finalAmount));
    }

    @Test
    public void test3() {
        int rentalDays = 5;
        int discount = 25;
        LocalDate checkoutDate = LocalDate.of(2015, 7, 2);
        LocalDate dueDate = checkoutDate.plusDays(rentalDays);

        LocalDate independenceDay = inventory.holidayMap.get(DateHelper.getHolidayKey(DateHelper.getObservedIndependenceDay(2015)));
        assertNotNull(independenceDay);

        ToolType toolType = inventory.toolsMap.get(Tool.TOOL_CODE_CHNS).getToolType();
        int chargeDays = inventory.getChargeDays(checkoutDate, dueDate, toolType);
        assertEquals(3, chargeDays);

        float dailyCharge = toolType.getDailyCharge();
        assertEquals(1.49f, dailyCharge);

        float preDiscountCharge = chargeDays * dailyCharge;
        assertEquals("$4.47", NumberFormat.getCurrencyInstance().format(preDiscountCharge));

        float discountAmount = ((float) discount / 100) * preDiscountCharge;
        assertEquals("$1.12", NumberFormat.getCurrencyInstance().format(discountAmount));

        float finalAmount = preDiscountCharge - discountAmount;
        assertEquals("$3.35", NumberFormat.getCurrencyInstance().format(finalAmount));
    }

    @Test
    public void test4() {
        int rentalDays = 6;
        int discount = 0;
        LocalDate checkoutDate = LocalDate.of(2015, 9, 3);
        LocalDate dueDate = checkoutDate.plusDays(rentalDays);

        LocalDate laborDay = inventory.holidayMap.get(DateHelper.getHolidayKey(DateHelper.getLaborDay(2015)));
        assertNotNull(laborDay);

        ToolType toolType = inventory.toolsMap.get(Tool.TOOL_CODE_JAKD).getToolType();
        int chargeDays = inventory.getChargeDays(checkoutDate, dueDate, toolType);
        assertEquals(3, chargeDays);

        float dailyCharge = toolType.getDailyCharge();
        assertEquals(2.99f, dailyCharge);

        float preDiscountCharge = chargeDays * dailyCharge;
        assertEquals("$8.97", NumberFormat.getCurrencyInstance().format(preDiscountCharge));

        float discountAmount = ((float) discount / 100) * preDiscountCharge;
        assertEquals("$0.00", NumberFormat.getCurrencyInstance().format(discountAmount));

        float finalAmount = preDiscountCharge - discountAmount;
        assertEquals("$8.97", NumberFormat.getCurrencyInstance().format(finalAmount));
    }

    @Test
    public void test5() {
        int rentalDays = 9;
        int discount = 0;
        LocalDate checkoutDate = LocalDate.of(2015, 7, 2);
        LocalDate dueDate = checkoutDate.plusDays(rentalDays);

        LocalDate independenceDay = inventory.holidayMap.get(DateHelper.getHolidayKey(DateHelper.getObservedIndependenceDay(2015)));
        assertNotNull(independenceDay);

        ToolType toolType = inventory.toolsMap.get(Tool.TOOL_CODE_JAKR).getToolType();
        int chargeDays = inventory.getChargeDays(checkoutDate, dueDate, toolType);
        assertEquals(5, chargeDays);

        float dailyCharge = toolType.getDailyCharge();
        assertEquals(2.99f, dailyCharge);

        float preDiscountCharge = chargeDays * dailyCharge;
        assertEquals("$14.95", NumberFormat.getCurrencyInstance().format(preDiscountCharge));

        float discountAmount = ((float) discount / 100) * preDiscountCharge;
        assertEquals("$0.00", NumberFormat.getCurrencyInstance().format(discountAmount));

        float finalAmount = preDiscountCharge - discountAmount;
        assertEquals("$14.95", NumberFormat.getCurrencyInstance().format(finalAmount));
    }

    @Test
    public void test6() {
        int rentalDays = 4;
        int discount = 50;
        LocalDate checkoutDate = LocalDate.of(2020, 7, 2);
        LocalDate dueDate = checkoutDate.plusDays(rentalDays);

        LocalDate independenceDay = inventory.holidayMap.get(DateHelper.getHolidayKey(DateHelper.getObservedIndependenceDay(2020)));
        assertNotNull(independenceDay);

        ToolType toolType = inventory.toolsMap.get(Tool.TOOL_CODE_JAKR).getToolType();
        int chargeDays = inventory.getChargeDays(checkoutDate, dueDate, toolType);
        assertEquals(1, chargeDays);

        float dailyCharge = toolType.getDailyCharge();
        assertEquals(2.99f, dailyCharge);

        float preDiscountCharge = chargeDays * dailyCharge;
        assertEquals("$2.99", NumberFormat.getCurrencyInstance().format(preDiscountCharge));

        float discountAmount = ((float) discount / 100) * preDiscountCharge;
        assertEquals("$1.50", NumberFormat.getCurrencyInstance().format(discountAmount));

        float finalAmount = preDiscountCharge - discountAmount;
        assertEquals("$1.50", NumberFormat.getCurrencyInstance().format(finalAmount));
    }
}
