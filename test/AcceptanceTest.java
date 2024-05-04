import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.text.NumberFormat;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class AcceptanceTest {
    static Inventory inventory;

    @BeforeAll
    public static void setup() {
        inventory = Inventory.getInstance();

        inventory.addHoliday(DateHelper.getObservedIndependenceDay(2015));
        inventory.addHoliday(DateHelper.getLaborDay(2015));
        inventory.addHoliday(DateHelper.getObservedIndependenceDay(2020));
        inventory.addHoliday(DateHelper.getLaborDay(2020));

        ToolType toolTypeLadder = new ToolType("Ladder", 1.99f, true, true, false);
        ToolType toolTypeChainsaw = new ToolType("Ladder", 1.49f, true, false, true);
        ToolType toolTypeJackhammer = new ToolType("Jackhammer", 2.99f, true, false, false);

        Tool toolLadder = new Tool("LADW", toolTypeLadder, "Werner");
        Tool toolChainsaw = new Tool("CHNS", toolTypeChainsaw, "Stihl");
        Tool toolJackhammer = new Tool("JAKD", toolTypeJackhammer, "DeWalt");
        Tool toolJackhammer2 = new Tool("JAKR", toolTypeJackhammer, "Ridgid");

        inventory.addTool(toolLadder);
        inventory.addTool(toolChainsaw);
        inventory.addTool(toolJackhammer);
        inventory.addTool(toolJackhammer2);
    }

    @Test
    public void test1() {
        LocalDate checkoutDate = LocalDate.of(2015, 9, 3);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            inventory.checkout("JAKR", 5, 101, checkoutDate);
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

        //TODO Extract hard coded strings
        ToolType toolType = inventory.toolsMap.get("LADW").getToolType();
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

}
/*
*        Test 1 Test 2 Test 3 Test 4 Test 5 Test 6
Tool code JAKR LADW CHNS JAKD JAKR JAKR
Checkout date 9/3/15 7/2/20 7/2/15 9/3/15 7/2/15 7/2/20
Rental days 5 3 5 6 9 4
Discount 101% 10% 25% 0% 0% 50%
*
*
*
* */