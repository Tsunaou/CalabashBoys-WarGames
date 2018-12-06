package calabashBrothers.formation;

import calabashBrothers.GUI.Coordinate;
import calabashBrothers.GUI.Maps;
import calabashBrothers.beings.Creature;

import java.util.ArrayList;

/**
 * @ Author     ：Young
 * @ Description：偃月阵
 */
public class YanYue extends Formation{
    public YanYue(int startX, int startY) {
        super(startX, startY);
        initSites(startX,startY);
    }

    @Override
    void initSites(int x, int y) {
        sites.add(new Coordinate(x,y));
        sites.add(new Coordinate(x,y+1));
        sites.add(new Coordinate(x,y+2));
        sites.add(new Coordinate(x-1,y));
        sites.add(new Coordinate(x-1,y+1));
        sites.add(new Coordinate(x-1,y+2));
        sites.add(new Coordinate(x+1,y));
        sites.add(new Coordinate(x+1,y+1));
        sites.add(new Coordinate(x+1,y+2));
        sites.add(new Coordinate(x-2,y+2));
        sites.add(new Coordinate(x-2,y+3));
        sites.add(new Coordinate(x-3,y+3));
        sites.add(new Coordinate(x-3,y+4));
        sites.add(new Coordinate(x-4,y+5));
        sites.add(new Coordinate(x+2,y+2));
        sites.add(new Coordinate(x+2,y+3));
        sites.add(new Coordinate(x+3,y+3));
        sites.add(new Coordinate(x+3,y+4));
        sites.add(new Coordinate(x+4,y+5));
    }

    @Override
    public void SetFormation(Maps<Creature> maps, ArrayList<Creature> creatures, int direction) {
        for(int i=0;i<=18;i++){
            maps.setContent(sites.get(i).getX(),sites.get(i).getY(),creatures.get(i));
        }
    }
}
