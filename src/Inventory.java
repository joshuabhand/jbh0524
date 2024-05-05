import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inventory {
    private static Inventory inventory;
    protected final Map<String, Tool> toolsMap;
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

    public void addTool(Tool tool) {
        toolsMap.put(tool.getToolCode(), tool);
    }

    public void addHoliday(LocalDate holiday) {
        String holidayKey = DateHelper.getHolidayKey(holiday);
        holidayMap.put(holidayKey, holiday);
    }

//    ● Charge days - Count of chargeable days, from day after checkout through and including due
//    date, excluding “no charge” days as specified by the tool type.
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

    public String checkout(String toolCode, int rentalDayCount, int discountPercent, LocalDate checkoutDate) throws IllegalArgumentException {
        if(rentalDayCount < 1) {
            throw new IllegalArgumentException("Rental day count must be 1 or greater");
        }
        if(discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Discount percent must be in the range of 0 - 100");
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");

        Tool tool = toolsMap.get(toolCode);
        List<String> rentalAgreement = new ArrayList<>();

        rentalAgreement.add("Tool code: " + tool.getToolCode());
        rentalAgreement.add("Tool type: " + tool.getToolType().getToolTypeName());
        rentalAgreement.add("Tool brand: " + tool.getToolBrand());
        rentalAgreement.add("Rental days: " + rentalDayCount);
        rentalAgreement.add("Check out date: " + checkoutDate.format(dateTimeFormatter));

        LocalDate dueDate = checkoutDate.plusDays(rentalDayCount);
        rentalAgreement.add("Due date: " + dueDate.format(dateTimeFormatter));

        float dailyCharge = tool.getToolType().getDailyCharge();
        rentalAgreement.add("Daily rental charge: " + NumberFormat.getCurrencyInstance().format(dailyCharge));

        int chargeDays = getChargeDays(checkoutDate, dueDate, tool.getToolType());
        rentalAgreement.add("Charge days: " + chargeDays);

        float preDiscountCharge = chargeDays * dailyCharge;
        rentalAgreement.add("Pre-discount charge: " + NumberFormat.getCurrencyInstance().format(preDiscountCharge));

        rentalAgreement.add("Discount percent: " + String.format("%d%%", discountPercent));

        float discountAmount = ((float) discountPercent / 100) * preDiscountCharge;
        rentalAgreement.add("Discount amount: " + NumberFormat.getCurrencyInstance().format(discountAmount));

        rentalAgreement.add("Final charge: " + NumberFormat.getCurrencyInstance().format(preDiscountCharge - discountAmount));

        return String.join("\n", rentalAgreement);
    }
}
        /*

● Pre-discount charge - Calculated as charge days X daily charge. Resulting total rounded half up
to cents.
● Discount amount - calculated from discount % and pre-discount charge. Resulting amount
rounded half up to cents.
● Final charge - Calculated as pre-discount charge - discount amount.

with formatting as follows:
● Date mm/dd/yy
● Currency $9,999.99
● Percent 99%
        * */