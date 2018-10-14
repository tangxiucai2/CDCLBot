package cdcl.bot.roster.staff;

public enum StaffType {
    MANAGEMENT(":key:"),
    REFEREE(":shield:"),
    ADVISOR(":scales:"),
    HOST(":watermelon:"),
    MEDIA(":video_camera:");

    private String tag;

    StaffType(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
}
