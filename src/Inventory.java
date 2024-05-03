import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Inventory {
    private static Inventory inventory;
    private final List<Tool> tools;

    private Inventory() {
        tools = new ArrayList<>();
    }

    public static Inventory getInstance() {
        if(inventory == null) {
            inventory = new Inventory();
        }

        return inventory;
    }

    public void addNewTool(Tool tool) {
        tools.add(tool);
    }

    public String checkout(Tool tool, int rentalDayCount, int discountPercent, Calendar checkoutDate) {
        StringBuilder stringBuilder = new StringBuilder();

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

//        stringBuilder.append("Daily rental charge: ");
//        stringBuilder.append();
//        stringBuilder.append("\n");

//        stringBuilder.append("Charge days: ");
//        stringBuilder.append();
//        stringBuilder.append("\n");

//        stringBuilder.append("Pre-discount charge: ");
//        stringBuilder.append();
//        stringBuilder.append("\n");

//        stringBuilder.append("Discount percent: ");
//        stringBuilder.append();
//        stringBuilder.append("\n");

//        stringBuilder.append("Discount amount: ");
//        stringBuilder.append();
//        stringBuilder.append("\n");

//        stringBuilder.append("Final charge: ");
//        stringBuilder.append();
//        stringBuilder.append("\n");

        return stringBuilder.toString();
        /*
● Daily rental charge - Amount per day, specified by the tool type.
● Charge days - Count of chargeable days, from day after checkout through and including due
date, excluding “no charge” days as specified by the tool type.
● Pre-discount charge - Calculated as charge days X daily charge. Resulting total rounded half up
to cents.
● Discount percent - Specified at checkout.
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
