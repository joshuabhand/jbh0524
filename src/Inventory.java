import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The inventory class maintains the tool and holiday list.
 * It's currently a singleton to ensure one data source for tools and holidays.
 */
public class Inventory {
    private static Inventory inventory;

    /**
     * This field maintains the tools with the tool code as a key and the {@link Tool} object as a value
     */
    protected final Map<String, Tool> toolsMap;

    /**
     * This field maintains the holiday list with month/day as key i.e. "74" as "July 4"
     * and a date as the value. This key is obtained via {@link DateHelper}
     */
    protected final Map<String, LocalDate> holidayMap;

    private Inventory() {
        toolsMap = new HashMap<>();
        holidayMap = new HashMap<>();
    }

    public static Inventory getInstance() {
        if(inventory == null) {
            inventory = new Inventory();
        }

        return inventory;
    }

    /**
     * Add holiday to the list of holidays for inventory.
     *
     * @param tool tool to be added to the inventory of tools
     */
    public void addTool(Tool tool) {
        toolsMap.put(tool.getToolCode(), tool);
    }

    /**
     * Add holiday to the collection of holidays for inventory.
     *
     * @param holiday holiday to be added
     */
    public void addHoliday(LocalDate holiday) {
        String holidayKey = DateHelper.getHolidayKey(holiday);
        holidayMap.put(holidayKey, holiday);
    }

    /**
     * Count of chargeable days, from day after checkout through and including due
     * date, excluding “no charge” days as specified by the tool type.
     *
     * @param checkoutDate the date the tool is checked out
     * @param dueDate the date the tool is due for
     * @param toolType the tool type being checked out
     *
     * @return the number of days to charge for
     */
    protected int getChargeDays(LocalDate checkoutDate, LocalDate dueDate, ToolType toolType) {
        int chargeDays = 0;
        LocalDate startDate = checkoutDate.plusDays(1);
        LocalDate endDate = dueDate.plusDays(1);

        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            String dayOfWeek = date.getDayOfWeek().toString();
            boolean isWeekend = "Sunday".equalsIgnoreCase(dayOfWeek) || "Saturday".equalsIgnoreCase(dayOfWeek);
            boolean isHoliday = holidayMap.get(DateHelper.getHolidayKey(date)) != null;

            if(toolType.isHolidayChargeable() && isHoliday) {
                chargeDays++;
            }
            else if(toolType.isWeekendChargeable() && isWeekend) {
                chargeDays++;
            }
            else if(toolType.isWeekdayChargeable() && !isWeekend && !isHoliday) {
                chargeDays++;
            }
        }

        return chargeDays;
    }

    /**
     * Checkout generates a Rental Agreement with the tool details, the charge days and the amount due.
     *
     * @param toolCode the code for which tool to check out
     * @param rentalDayCount the amount of days to check out the tool
     * @param discountPercent the discount value for the transaction
     * @param checkoutDate the date the tool is checked out
     *
     * @return the rental agreement as a string value
     */
    public String checkout(String toolCode, int rentalDayCount, int discountPercent, LocalDate checkoutDate) throws IllegalArgumentException {
        if(rentalDayCount < 1) {
            throw new IllegalArgumentException("Rental day count must be 1 or greater");
        }
        if(discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Discount percent must be in the range of 0 - 100");
        }

        // Date format mm/dd/yy i.e 05/03/24
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");

        // Currency format $9.99
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();

        Tool tool = toolsMap.get(toolCode);
        ToolType toolType = tool.getToolType();
        List<String> rentalAgreement = new ArrayList<>();

        rentalAgreement.add("Tool code: " + tool.getToolCode());
        rentalAgreement.add("Tool type: " + toolType.getToolTypeName());
        rentalAgreement.add("Tool brand: " + tool.getToolBrand());
        rentalAgreement.add("Rental days: " + rentalDayCount);
        rentalAgreement.add("Check out date: " + checkoutDate.format(dateTimeFormatter));

        // Calculated from checkout date and rental days.
        LocalDate dueDate = checkoutDate.plusDays(rentalDayCount);
        rentalAgreement.add("Due date: " + dueDate.format(dateTimeFormatter));

        // Amount per day, specified by the tool type.
        float dailyCharge = toolType.getDailyCharge();
        rentalAgreement.add("Daily rental charge: " + numberFormat.format(dailyCharge));

        int chargeDays = getChargeDays(checkoutDate, dueDate, toolType);
        rentalAgreement.add("Charge days: " + chargeDays);

        // Calculated as charge days X daily charge. Resulting total rounded half up to cents. //TODO what does this mean?
        float preDiscountCharge = chargeDays * dailyCharge;
        rentalAgreement.add("Pre-discount charge: " + numberFormat.format(preDiscountCharge));

        rentalAgreement.add("Discount percent: " + String.format("%d%%", discountPercent));

        // Calculated from discount % and pre-discount charge. Resulting amount rounded half up to cents.
        float discountAmount = ((float) discountPercent / 100) * preDiscountCharge;
        rentalAgreement.add("Discount amount: " + numberFormat.format(discountAmount));

        // Calculated as pre-discount charge - discount amount
        rentalAgreement.add("Final charge: " + numberFormat.format(preDiscountCharge - discountAmount));

        return String.join("\n", rentalAgreement);
    }
}
