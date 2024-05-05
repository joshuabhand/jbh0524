import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Inventory inventory = Inventory.getInstance();

        int currentYear = 2024;
        inventory.addHoliday(DateHelper.getObservedIndependenceDay(currentYear));
        inventory.addHoliday(DateHelper.getLaborDay(currentYear));

        ToolType toolTypeLadder = new ToolType(ToolType.TOOL_NAME_LADDER, 1.99f, true, true, false);
        ToolType toolTypeChainsaw = new ToolType(ToolType.TOOL_NAME_CHAINSAW, 1.49f, true, false, true);
        ToolType toolTypeJackhammer = new ToolType(ToolType.TOOL_NAME_JACKHAMMER, 2.99f, true, false, false);

        Tool toolLadder = new Tool(Tool.TOOL_CODE_LADDER, toolTypeLadder, Tool.TOOL_BRAND_WERNER);
        Tool toolChainsaw = new Tool(Tool.TOOL_CODE_CHAINSAW, toolTypeChainsaw, Tool.TOOL_BRAND_STIHL);
        Tool toolJackhammer = new Tool(Tool.TOOL_CODE_JACKHAMMER_D, toolTypeJackhammer, Tool.TOOL_BRAND_DEWALT);
        Tool toolJackhammer2 = new Tool(Tool.TOOL_CODE_JACKHAMMER_R, toolTypeJackhammer, Tool.TOOL_BRAND_RIDGID);

        inventory.addTool(toolLadder);
        inventory.addTool(toolChainsaw);
        inventory.addTool(toolJackhammer);
        inventory.addTool(toolJackhammer2);

        try {
            String checkoutLadderRental = inventory.checkout(Tool.TOOL_CODE_LADDER, 5, 20, LocalDate.of(2024, 5, 3));
            System.out.println(checkoutLadderRental);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
