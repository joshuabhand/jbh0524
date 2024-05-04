import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private static Inventory inventory;
    private final Map<String, Tool> toolsMap;
    private final Map<String, LocalDate> holidayMap;//TODO use int instead of String as key?

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
        holidayMap.put(holiday.toString(), holiday);//TODO update logic
    }

//    ● Charge days - Count of chargeable days, from day after checkout through and including due
//    date, excluding “no charge” days as specified by the tool type.
    private int getChargeDays(LocalDate checkoutDate, LocalDate dueDate) {
        int chargeDays = 0;
        LocalDate startDate = checkoutDate.plusDays(1);
        LocalDate endDate = dueDate.plusDays(1);

        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {

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

        StringBuilder stringBuilder = new StringBuilder();
        Tool tool = toolsMap.get(toolCode);//TODO check if existing

        stringBuilder.append("Tool code: ");
        stringBuilder.append(tool.getToolCode());
        stringBuilder.append("\n");

        stringBuilder.append("Tool type: ");
        stringBuilder.append(tool.getToolType().getToolTypeName());
        stringBuilder.append("\n");

        stringBuilder.append("Tool brand: ");
        stringBuilder.append(tool.getBrand());
        stringBuilder.append("\n");

        stringBuilder.append("Rental days: ");
        stringBuilder.append(rentalDayCount);
        stringBuilder.append("\n");

        stringBuilder.append("Check out date: ");
        stringBuilder.append(checkoutDate.format(dateTimeFormatter));
        stringBuilder.append("\n");

        LocalDate dueDate = checkoutDate.plusDays(rentalDayCount);
        stringBuilder.append("Due date: ");
        stringBuilder.append(dueDate.format(dateTimeFormatter));
        stringBuilder.append("\n");

        float dailyCharge = tool.getToolType().getDailyCharge();
        stringBuilder.append("Daily rental charge: ");
        stringBuilder.append(NumberFormat.getCurrencyInstance().format(dailyCharge));
        stringBuilder.append("\n");

//        int chargeDays = getChargeDays(checkoutDate, dueDate);
        int chargeDays = 2;//TEMP
        stringBuilder.append("Charge days: ");
        stringBuilder.append(chargeDays);
        stringBuilder.append("\n");

        float preDiscountCharge = chargeDays * dailyCharge;
        stringBuilder.append("Pre-discount charge: ");
        stringBuilder.append(NumberFormat.getCurrencyInstance().format(preDiscountCharge));
        stringBuilder.append("\n");

        stringBuilder.append("Discount percent: ");
        stringBuilder.append(String.format("%d%%",discountPercent));
        stringBuilder.append("\n");

        float discountAmount = ((float) discountPercent / 100) * preDiscountCharge;
        stringBuilder.append("Discount amount: ");
        stringBuilder.append(NumberFormat.getCurrencyInstance().format(discountAmount));
        stringBuilder.append("\n");

        stringBuilder.append("Final charge: ");
        stringBuilder.append(NumberFormat.getCurrencyInstance().format(preDiscountCharge - discountAmount));
        stringBuilder.append("\n");

        return stringBuilder.toString();
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
    }
}
