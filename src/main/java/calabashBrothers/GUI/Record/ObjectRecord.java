package calabashBrothers.GUI.Record;

import calabashBrothers.GUI.Coordinate;
import calabashBrothers.beings.Creature;

/**
 * @ Author     ：Young
 * @ Date       ：Created in 21:01 2018/12/19
 * @ Description：场上人物的容器
 */
public class ObjectRecord {

    private String object;    //是谁
    private double HP_Remain;
    private Coordinate pos;

    public ObjectRecord(String object, double HP_Remain, Coordinate pos) {
        this.object = object;
        this.HP_Remain = HP_Remain;
        this.pos = pos;
    }

    public String getObject() {
        return object;
    }

    public double getHP_Remain() {
        return HP_Remain;
    }

    public Coordinate getPos() {
        return pos;
    }
}
