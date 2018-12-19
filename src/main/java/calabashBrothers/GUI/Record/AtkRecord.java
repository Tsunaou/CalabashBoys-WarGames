package calabashBrothers.GUI.Record;

import calabashBrothers.GUI.Coordinate;
import calabashBrothers.beings.Creature;

/**
 * @ Author     ：Young
 * @ Date       ：Created in 20:58 2018/12/19
 * @ Description：攻击的记录容器
 */
public class AtkRecord {
    private String attaker;//攻击者
    private Coordinate atkFrom; //攻击者的坐标
    private Coordinate atrTo;   //被攻击者的坐标

    public AtkRecord(String attaker, Coordinate atkFrom, Coordinate atrTo) {
        this.attaker = attaker;
        this.atkFrom = atkFrom;
        this.atrTo = atrTo;
    }

    public String getAttaker() {
        return attaker;
    }

    public Coordinate getAtkFrom() {
        return atkFrom;
    }

    public Coordinate getAtrTo() {
        return atrTo;
    }
}
