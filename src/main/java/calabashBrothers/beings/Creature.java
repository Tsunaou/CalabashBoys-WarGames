package calabashBrothers.beings;


import calabashBrothers.GUI.Config;
import calabashBrothers.GUI.Coordinate;
import calabashBrothers.GUI.Maps;
import calabashBrothers.beings.enums.Camp;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Random;

/**
 * @ Author     ：Young
 * @ Description：世界初生，有了生物
 */


public class Creature extends Beings implements Runnable, Config ,Fighting{

    private double HP_All;
    private double HP_Remain;
    private double Attack;
    private double Defence;

    public boolean Living;    //是否存活
    public Camp camp;          //阵营，防止自相残杀


    public static Maps<Creature> maps;   //static变量，所有的生物共享一个maps

    public Creature(String name) {
        super(name);
        this.Living = true;
        this.HP_All = 100;
        this.HP_Remain = 70;
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

    @Override
    public void Fighting(int x, int y) {
        //TODO
        synchronized (maps){
            String musicfile = "";
            switch (maps.getContent(x,y).getCamp()){
                case JUSTICE: musicfile = this.getClass().getClassLoader().getResource("media/weapons.mp3").toString();break;
                case EVIL:musicfile = this.getClass().getClassLoader().getResource("media/damage1.mp3").toString();break;
                case DEAD:return;
            }
            Media media2 = new Media(musicfile);
            MediaPlayer mp2 = new MediaPlayer(media2);
            mp2.play();
            double hp = maps.getContent(x,y).getHP_Remain();
            hp -= 30;
            if(hp<=0){
                maps.getContent(x,y).setHP_Remain(0.1);
                maps.getContent(x,y).setLiving(false);
                maps.setContent(x,y,null);
                System.err.println("setDeath");
                maps.setContent(x,y,new DeathObject());
            }else{
                maps.getContent(x,y).setHP_Remain(hp);
            }
        }
    }

    @Override
    public void run() {
        while (Living){
            synchronized (maps){
                System.out.println("run:"+this.toString());
                for(int i=-1;i<=1;i++){
                    for(int j=-1;j<=1;j++){
                        if(i==0 && j==0){
                            //自己杀自己，不行的
                        }else{
                            int xx = location.getX()+i;
                            int yy = location.getY()+j;
                            if(insideMaps(xx,yy)){
                                synchronized (maps){
                                    if(maps.getContent(xx,yy)!=null && maps.getContent(xx,yy).getCamp()!=this.camp){
                                        Fighting(xx,yy);
                                    }
                                }
                            }
                        }
                    }
                }
                moveRandom();
            }
            beingSleep(1000);
        }
    }

    public double getHP_All() {
        return HP_All;
    }

    public void setHP_All(double HP_All) {
        this.HP_All = HP_All;
    }

    public double getHP_Remain() {
        return HP_Remain;
    }

    public void setHP_Remain(double HP_Remain) {
        this.HP_Remain = HP_Remain;
    }

    public void setLiving(boolean living) {
        Living = living;
    }

    public Camp getCamp() {
        return camp;
    }

    public void setCamp(Camp camp) {
        this.camp = camp;
    }
}

