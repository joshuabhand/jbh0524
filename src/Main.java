import java.time.LocalDate;
import java.util.GregorianCalendar;

public class Main {
    public static void main(String[] args) {
        Inventory inventory = Inventory.getInstance();

//        ● Independence Day, July 4th - If falls on weekend, it is observed on the closest weekday (if Sat,
//                then Friday before, if Sunday, then Monday after)
//● Labor Day - First Monday in September
//        inventory.addHoliday(LocalDate.of(9999, 7, 4));
//        inventory.addHoliday(LocalDate.of(9999, 7, 4));

        int year = 2020;
        LocalDate date = LocalDate.of(year, 7, 4);

        System.out.println("4th of July: " + date.getDayOfWeek());
        System.out.println("Observed 4th: " + DateHelper.getObservedIndependenceDayOfWeek(year).getDayOfWeek());
        System.out.println();
        LocalDate date2 = LocalDate.of(year, 9, 2);
        System.out.println("First Monday of September: " + DateHelper.getFirstMondayOfSeptember(year));
        System.out.println("Correct Monday of September: " + date2.getDayOfMonth());


        ToolType toolTypeLadder = new ToolType("Ladder", 1.99f, true, true, false);
        Tool toolLadder = new Tool("LADW", toolTypeLadder, "Werner");
        inventory.addTool(toolLadder);

        String checkoutLadderRental;
        try {
            checkoutLadderRental = inventory.checkout("LADW", 5, 20, LocalDate.of(2024, 5, 3));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

//        System.out.println(checkoutLadderRental);
    }
}
