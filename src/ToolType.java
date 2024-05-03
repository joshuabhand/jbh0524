public class ToolType {
    private String toolTypeName;
    private float dailyCharge;
    private boolean weekdayCharge;
    private boolean weekendCharge;
    private boolean holidayCharge;

    public ToolType(String toolTypeName, float dailyCharge, boolean weekdayCharge, boolean weekendCharge, boolean holidayCharge) {
        this.toolTypeName = toolTypeName;
        this.dailyCharge = dailyCharge;
        this.weekdayCharge = weekdayCharge;
        this.weekendCharge = weekendCharge;
        this.holidayCharge = holidayCharge;
    }

    public String getToolTypeName() {
        return toolTypeName;
    }

    public void setToolTypeName(String toolTypeName) {
        this.toolTypeName = toolTypeName;
    }

    public float getDailyCharge() {
        return dailyCharge;
    }

    public void setDailyCharge(float dailyCharge) {
        this.dailyCharge = dailyCharge;
    }

    public boolean getWeekdayCharge() {
        return weekdayCharge;
    }

    public void setWeekdayCharge(boolean weekdayCharge) {
        this.weekdayCharge = weekdayCharge;
    }

    public boolean getWeekendCharge() {
        return weekendCharge;
    }

    public void setWeekendCharge(boolean weekendCharge) {
        this.weekendCharge = weekendCharge;
    }

    public boolean getHolidayCharge() {
        return holidayCharge;
    }

    public void setHolidayCharge(boolean holidayCharge) {
        this.holidayCharge = holidayCharge;
    }
}