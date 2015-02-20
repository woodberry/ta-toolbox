package au.net.woodberry.ta.toolbox.enums;

/**
 * Define periods and their group classifications
 */
public enum Period {

    // Short term moving averages
    THREE(3, Group.SHORTTERM),
    FIVE(5, Group.SHORTTERM),
    SEVEN(7, Group.SHORTTERM),
    EIGHT(8, Group.SHORTTERM),
    NINE(9, Group.SHORTTERM),
    TEN(10, Group.SHORTTERM),
    ELEVEN(11, Group.SHORTTERM),
    TWELVE(12, Group.SHORTTERM),
    THIRTEEN(13, Group.SHORTTERM),
    FIFTEEN(15, Group.SHORTTERM),

    // Long term moving averages
    TWENTYONE(21, Group.LONGTERM),
    TWENTYFOUR(24, Group.LONGTERM),
    TWENTYSEVEN(27, Group.LONGTERM),
    THIRTY(30, Group.LONGTERM),
    THIRTYTHREE(33, Group.LONGTERM),
    THIRTYFIVE(35, Group.LONGTERM),
    THIRTYSIX(36, Group.LONGTERM),
    FORTY(40, Group.LONGTERM),
    FORTYFIVE(45, Group.LONGTERM),
    FIFTY(50, Group.LONGTERM),
    SIXTY(60, Group.LONGTERM);

    private final int timeFrame;
    private final Group group;

    Period(int timeFrame, Group group) {
        this.timeFrame = timeFrame;
        this.group = group;
    }

    public Group getGroup() {
        return group;
    }

    public int getTimeFrame() {
        return timeFrame;
    }
}