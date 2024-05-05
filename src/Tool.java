public class Tool {
    public static final String TOOL_CODE_LADDER = "LADW";
    public static final String TOOL_CODE_CHAINSAW = "CHNS";
    public static final String TOOL_CODE_JACKHAMMER_D = "JAKD";
    public static final String TOOL_CODE_JACKHAMMER_R = "JAKR";

    public static final String TOOL_BRAND_STIHL = "Stihl";
    public static final String TOOL_BRAND_WERNER = "Werner";
    public static final String TOOL_BRAND_DEWALT = "DeWalt";
    public static final String TOOL_BRAND_RIDGID = "Ridgid";

    private String toolCode;
    private ToolType toolType;
    private String toolBrand;

    public Tool(String toolCode, ToolType toolType, String toolBrand) {
        this.toolCode = toolCode;
        this.toolType = toolType;
        this.toolBrand = toolBrand;
    }

    public String getToolCode() {
        return toolCode;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
    }

    public ToolType getToolType() {
        return toolType;
    }

    public void setToolType(ToolType toolType) {
        this.toolType = toolType;
    }

    public String getToolBrand() {
        return toolBrand;
    }

    public void setToolBrand(String toolBrand) {
        this.toolBrand = toolBrand;
    }
}
