public class Main {
    public static void main(String[] args) {
        System.out.println("Start");
        Inventory inventory = Inventory.getInstance();

        ToolType toolTypeLadder = new ToolType("Ladder", 1.99f, true, true, false);
        Tool toolLadder = new Tool("LADW", toolTypeLadder, "Werner");
        inventory.addNewTool(toolLadder);



    }
}
