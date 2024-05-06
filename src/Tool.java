/**
 * This class is a POJO representing the Tool object.
 */
public class Tool {
    public static final String TOOL_CODE_LADW = "LADW";
    public static final String TOOL_CODE_CHNS = "CHNS";
    public static final String TOOL_CODE_JAKD = "JAKD";
    public static final String TOOL_CODE_JAKR = "JAKR";

    public static final String TOOL_BRAND_STIHL = "Stihl";
    public static final String TOOL_BRAND_WERNER = "Werner";
    public static final String TOOL_BRAND_DEWALT = "DeWalt";
    public static final String TOOL_BRAND_RIDGID = "Ridgid";

    private String toolCode;
    private ToolTypeEnum toolType;
    private String toolBrand;

    public Tool(String toolCode, ToolTypeEnum toolType, String toolBrand) {
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

    public ToolTypeEnum getToolType() {
        return toolType;
    }

    public void setToolType(ToolTypeEnum toolType) {
        this.toolType = toolType;
    }

    public String getToolBrand() {
        return toolBrand;
    }

    public void setToolBrand(String toolBrand) {
        this.toolBrand = toolBrand;
    }
}
