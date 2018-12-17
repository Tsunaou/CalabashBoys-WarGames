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

    private double HP;

    private static Maps<Creature> maps;   //static变量，所有的生物共享一个maps

    public Creature(String name) {
        super(name);
    }

    public void selfIntroduction() {
        System.out.println("我的种类是" + this.getClass().getSimpleName());
    }

    public static Maps getMaps() {
        return maps;
    }

    synchronized public static void setMaps(Maps maps) {
        Creature.maps = maps;
    }

    public boolean insideMaps(int x,int y){
        return x>=0 && y>=0 && x<Height && y<Width;
    }

    synchronized public void moveRandom(){
        Random rand = new Random();
        int newX = 0;
        int newY = 0;
        try{
            newX = location.getX() + rand.nextInt(3)-1;
            newY = location.getY() + rand.nextInt(3)-1;
            while (!insideMaps(newX,newY)){
                newX = location.getX() + rand.nextInt(3)-1;
                newY = location.getY() + rand.nextInt(3)-1;
            }
        }catch (NullPointerException e){
            System.out.println(this.getClass().getSimpleName());
        }

        synchronized (maps){
            if(maps.empty(newX,newY)){
                maps.setContent(location.getX(),location.getY(),null);
                location = new Coordinate(newX,newY);
                maps.setContent(newX,newY,this);
            }
        }
    }

    //实现多线程的
    public void run(){
        while (true){
            synchronized (maps){
                //System.out.println("run:"+this.toString());
                moveRandom();
            }
            beingSleep(1000);
        }
    }
}

