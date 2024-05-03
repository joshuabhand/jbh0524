import java.util.ArrayList;
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
}
