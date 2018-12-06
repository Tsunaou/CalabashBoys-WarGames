package calabashBrothers.formation;

import calabashBrothers.GUI.Maps;
import calabashBrothers.beings.Creature;

import java.util.ArrayList;

/**
 * @ Author     ：Young
 * @ Description：雁行阵
 */
public class YanXing extends Formation{
    public YanXing(int startX, int startY) {
        super(startX, startY);
    }

    @Override
    void initSites(int x, int y) {

    }

    @Override
    public void SetFormation(Maps<Creature> maps, ArrayList<Creature> creatures, int direction) {
        for (int i = 0; i <creatures.size() ; i++) {
            maps.setContent(startX+i,startY-i,creatures.get(i));
        }
    }

}
