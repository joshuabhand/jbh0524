public enum ToolTypeEnum {
    LADDER ("Ladder", 1.99f, true, true, false),
    CHAINSAW ("Chainsaw", 1.49f, true, false, true),
    JACKHAMMER ("Jackhammer", 2.99f, true, false, false);

    private final String toolTypeName;
    private final float dailyCharge;
    private final boolean weekdayCharge;
    private final boolean weekendCharge;
    private final boolean holidayCharge;

    ToolTypeEnum(String toolTypeName, float dailyCharge, boolean weekdayCharge, boolean weekendCharge, boolean holidayCharge) {
        this.toolTypeName = toolTypeName;
        this.dailyCharge = dailyCharge;
        this.weekdayCharge = weekdayCharge;
        this.weekendCharge = weekendCharge;
        this.holidayCharge = holidayCharge;
    }

    public String getToolTypeName() {
        return toolTypeName;
    }

    public float getDailyCharge() {
        return dailyCharge;
    }

    public boolean isWeekdayChargeable() {
        return weekdayCharge;
    }

    public boolean isWeekendChargeable() {
        return weekendCharge;
    }

    public boolean isHolidayChargeable() {
        return holidayCharge;
    }
}