import java.util.Calendar;
import java.util.GregorianCalendar;

public class Main {
    public static void main(String[] args) {
        Inventory inventory = Inventory.getInstance();

        ToolType toolTypeLadder = new ToolType("Ladder", 1.99f, true, true, false);
        Tool toolLadder = new Tool("LADW", toolTypeLadder, "Werner");
        inventory.addNewTool(toolLadder);

        String checkoutLadderRental = inventory.checkout(toolLadder, 5, 20, new GregorianCalendar(2024, Calendar.MAY, 3));
        System.out.println(checkoutLadderRental);

    }
}
