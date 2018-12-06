package calabashBrothers.beings.enums;

public enum Color {
    RED("红色"), ORANGE("橙色"), YELLOW("黄色"), GREEN("绿色"), VERDANT("青色"), BLUE("蓝色"), PURPLE("紫色"); // 红橙黄绿蓝靛紫

    private String colorName;
    private final int colorNumber = 7;

    Color(String colorName) {
        this.colorName = colorName;
    }

    public String getColorName() {
        return colorName;
    }

    public int getColorNumber() {
        return colorNumber;
    }
}