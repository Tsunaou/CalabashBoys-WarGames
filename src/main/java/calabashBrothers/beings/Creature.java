package calabashBrothers.beings;


import calabashBrothers.GUI.Config;
import calabashBrothers.GUI.Coordinate;
import calabashBrothers.GUI.Maps;
import calabashBrothers.GUI.unit;

import java.util.Random;

/**
 * @ Author     ：Young
 * @ Description：世界初生，有了生物
 */


public class Creature extends Beings implements Runnable, Config {

    private static Maps maps;   //static变量，所有的生物共享一个maps

    public Creature(String name) {
        super(name);
    }

    public void selfIntroduction() {
        System.out.println("我的种类是" + this.getClass().getSimpleName());
    }

    public static Maps getMaps() {
        return maps;
    }

    public static void setMaps(Maps maps) {
        Creature.maps = maps;
    }

    public boolean validUnit(int x,int y){
        return x>=0 && y>=0 && x<Height && y<Width;
    }

    public void moveRandom(){
        Random rand = new Random();
        int i = rand.nextInt(2);
        int j = rand.nextInt(2);
        while(!validUnit(-1+i,-1+j)){
            i = rand.nextInt(2);
            j = rand.nextInt(2);
        }
        if(maps.empty(i,j)){
            maps.setContent(this.location.getX(),this.location.getY(),null);
            this.location = new Coordinate(i,j);
            maps.setContent(i,j,this);
        }
    }

    //实现多线程的
    public void run(){
        synchronized (maps){
            moveRandom();
        }
    }
}

