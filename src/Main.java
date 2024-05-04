import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Inventory inventory = Inventory.getInstance();

        int currentYear = 2024;
        inventory.addHoliday(DateHelper.getObservedIndependenceDay(currentYear));
        inventory.addHoliday(DateHelper.getLaborDay(currentYear));

        ToolType toolTypeLadder = new ToolType("Ladder", 1.99f, true, true, false);
        Tool toolLadder = new Tool("LADW", toolTypeLadder, "Werner");
        inventory.addTool(toolLadder);

        String checkoutLadderRental;
        try {
            checkoutLadderRental = inventory.checkout("LADW", 5, 20, LocalDate.of(2024, 5, 3));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println(checkoutLadderRental);
    }
}

/* TEST DATA for dates
*         int year = 2020;
        LocalDate date = LocalDate.of(year, 7, 4);

        System.out.println("4th of July: " + date.getDayOfWeek());
        System.out.println("Observed 4th: " + DateHelper.getObservedIndependenceDay(year).getDayOfWeek());
        System.out.println();
        int laborYear = 2020;
        LocalDate date2 = LocalDate.of(laborYear, 9, 7);//Mon, Sep 7, 2020
        System.out.println("First Monday of September: " + DateHelper.getLaborDay(laborYear));
        System.out.println("Correct Monday of September: " + date2.getDayOfMonth());
*
* */