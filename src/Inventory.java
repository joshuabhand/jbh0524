import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private static Inventory inventory;
    private final Map<String, Tool> toolsMap;

    private Inventory() {
        toolsMap = new HashMap<>();
    }

    public static Inventory getInstance() {
        if(inventory == null) {
            inventory = new Inventory();
        }

        return inventory;
    }

    public void addNewTool(Tool tool) {
        toolsMap.put(tool.getToolCode(), tool);
    }

    public String checkout(String toolCode, int rentalDayCount, int discountPercent, Calendar checkoutDate) throws IllegalArgumentException {
        if(rentalDayCount < 1) {
            throw new IllegalArgumentException("Rental day count must be 1 or greater");
        }
        if(discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Discount percent must be in the range of 0 - 100");
        }

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

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
        stringBuilder.append("Check out date: ");
        stringBuilder.append(dateFormat.format(checkoutDate.getTime()));
        stringBuilder.append("\n");

        Calendar dueDate = (Calendar) checkoutDate.clone();
        dueDate.add(Calendar.DATE, rentalDayCount);
        stringBuilder.append("Due date: ");
        stringBuilder.append(dateFormat.format(dueDate.getTime()));
        stringBuilder.append("\n");

        stringBuilder.append("Daily rental charge: ");
        stringBuilder.append(NumberFormat.getCurrencyInstance().format(tool.getToolType().getDailyCharge()));
        stringBuilder.append("\n");

//        stringBuilder.append("Charge days: ");
//        stringBuilder.append();
//        stringBuilder.append("\n");

        stringBuilder.append("Pre-discount charge: ");
        stringBuilder.append(NumberFormat.getCurrencyInstance().format(5.99f));//TODO temp
        stringBuilder.append("\n");

        stringBuilder.append("Discount percent: ");
        stringBuilder.append(String.format("%d%%",discountPercent));
        stringBuilder.append("\n");

//        stringBuilder.append("Discount amount: ");
//        stringBuilder.append();
//        stringBuilder.append("\n");

//        stringBuilder.append("Final charge: ");
//        stringBuilder.append();
//        stringBuilder.append("\n");

        return stringBuilder.toString();
        /*
● Charge days - Count of chargeable days, from day after checkout through and including due
date, excluding “no charge” days as specified by the tool type.
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
