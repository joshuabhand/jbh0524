import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Inventory inventory = Inventory.getInstance();
        inventory.initializeInventory();

        int currentYear = 2024;
        inventory.addHoliday(DateHelper.getObservedIndependenceDay(currentYear));
        inventory.addHoliday(DateHelper.getLaborDay(currentYear));

        try {
            String checkoutLadderRental = inventory.checkout(Tool.TOOL_CODE_LADW, 5, 20, LocalDate.of(2024, 5, 3));
            System.out.println(checkoutLadderRental);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
