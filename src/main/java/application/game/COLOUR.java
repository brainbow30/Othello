package application.game;

public enum COLOUR {
    BLACK,
    WHITE;

    public static COLOUR opposite(COLOUR colour) {
        if (colour.equals(WHITE)) {
            return BLACK;
        } else {
            return WHITE;
        }
    }

    public static Integer getColourValue(COLOUR colour) {
        if (colour.equals(BLACK)) {
            return 1;
        } else if (colour.equals(WHITE)) {
            return -1;
        } else {
            return 0;
        }
    }
}
