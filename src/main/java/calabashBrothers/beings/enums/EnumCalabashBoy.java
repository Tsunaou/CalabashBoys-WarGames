package calabashBrothers.beings.enums;


public enum EnumCalabashBoy {

    RED_BOY("老大", Color.RED, 0),
    ORANGE_BOY("老二", Color.ORANGE, 1),
    YELLOW_BOY("老三", Color.YELLOW, 2),
    GREEN_BOY("老四", Color.GREEN, 3),
    VERDANT_BOY("老五", Color.VERDANT, 4),
    BLUE_BOY("老六", Color.BLUE, 5),
    PURPLE_BOY("老七", Color.PURPLE, 6);

    private String name;
    private Color color;
    private int rank;
    private final int number = 7;

    EnumCalabashBoy(String name, Color color, int rank) {
        this.name = name;
        this.color = color;
        this.rank = rank;
    }

    public String getName() {
        return this.name;
    }
    public Color getColor() {
        return this.color;
    }
    public int getRank() {
        return this.rank;
    }



}
