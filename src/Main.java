import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Inventory inventory = Inventory.getInstance();

        int currentYear = 2024;
        inventory.addHoliday(DateHelper.getObservedIndependenceDay(currentYear));
        inventory.addHoliday(DateHelper.getLaborDay(currentYear));

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

        try {
            String checkoutLadderRental = inventory.checkout("LADW", 5, 20, LocalDate.of(2024, 5, 3));
            System.out.println(checkoutLadderRental);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
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