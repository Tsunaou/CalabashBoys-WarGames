package calabashBrothers.GUI.Record;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @ Author     ：Young
 * @ Date       ：Created in 20:53 2018/12/19
 * @ Description：记录战斗记录（每次刷新画面时保存一次）
 *                  记录包括：Ojbect记录和Atk记录
 */
public class Recorder implements Serializable {

    ArrayList<ObjectRecord> objList = new ArrayList<>();
    ArrayList<AtkRecord> atkList = new ArrayList<>();

    public void addObjList(ObjectRecord obj) {
        objList.add(obj);
    }

    public void addAtkList(AtkRecord atk) {
        atkList.add(atk);
    }

    public ArrayList<ObjectRecord> getObjList() {
        return objList;
    }

    public ArrayList<AtkRecord> getAtkList() {
        return atkList;
    }

    @Override
    public String toString() {
        return "Recorder{" +
                "objList=" + objList +
                ", atkList=" + atkList +
                '}';
    }
}
