package calabashBrothers.formation;

import calabashBrothers.GUI.Coordinate;
import calabashBrothers.GUI.Maps;
import calabashBrothers.beings.Creature;

import java.util.ArrayList;

/**
 * @ Author     ：Young
 * @ Description：${description}
 */
abstract public class Formation {
    protected int startX;   //阵法领导者的X坐标
    protected int startY;   //阵法领导者的Y坐标

    ArrayList<Coordinate> sites = new ArrayList<Coordinate>();


    public Formation(int startX, int startY) {
        this.startX = startX;
        this.startY = startY;
    }
    abstract void initSites(int x,int y);
    abstract public void SetFormation(Maps<Creature> maps, ArrayList<Creature> creatures, int direction);

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }
}
