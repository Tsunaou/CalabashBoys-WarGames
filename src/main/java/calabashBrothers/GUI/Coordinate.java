package calabashBrothers.GUI;

import java.io.Serializable;

/**
 * @ Author     ：Young
 * @ Description：坐标
 */
public class Coordinate implements Serializable {
    private int x;  //横坐标
    private int y;  //纵坐标

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
