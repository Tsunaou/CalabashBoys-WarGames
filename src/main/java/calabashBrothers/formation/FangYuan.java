package calabashBrothers.formation;

import calabashBrothers.GUI.Coordinate;
import calabashBrothers.GUI.Maps;
import calabashBrothers.beings.Creature;

import java.util.ArrayList;

/**
 * @ Author     ：Young
 * @ Description：方円阵
 */
public class FangYuan extends Formation{

    private int midX;
    private int midY;


    public void initSites(int x,int y){
        sites.add(new Coordinate(x,y-3));
        sites.add(new Coordinate(x-1,y-2));
        sites.add(new Coordinate(x+1,y-2));
        sites.add(new Coordinate(x+2,y-1));
        sites.add(new Coordinate(x-2,y-1));
        sites.add(new Coordinate(x+3,y));
        sites.add(new Coordinate(x-3,y));
        sites.add(new Coordinate(x+2,y+1));
        sites.add(new Coordinate(x-2,y+1));
        sites.add(new Coordinate(x+1,y+2));
        sites.add(new Coordinate(x-1,y+2));
        sites.add(new Coordinate(x,y+3));
    }


    public FangYuan(int startX, int startY) {
        super(startX, startY);
        this.midX = startX;
        this.midY = startY+3;
        initSites(midX,midY);
    }

    @Override
    public void SetFormation(Maps<Creature> maps, ArrayList<Creature> creatures, int direction) {
        for(int i=0;i<=11;i++){
            maps.setContent(sites.get(i).getX(),sites.get(i).getY(),creatures.get(i));
        }
    }

}
