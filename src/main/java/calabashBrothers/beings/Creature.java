package calabashBrothers.beings;


import calabashBrothers.GUI.Config;
import calabashBrothers.GUI.Coordinate;
import calabashBrothers.GUI.Maps;
import calabashBrothers.beings.enums.Camp;
import calabashBrothers.beings.enums.Direction;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


import java.util.Random;

/**
 * @ Author     ：Young
 * @ Description：世界初生，有了生物
 */


public class Creature extends Beings implements Runnable, Config ,Fighting{

    //角色属性
    private double HP_All;      //总生命值
    private double HP_Remain;   //剩余生命值
    private double ATK;      //攻击力
    private double DEF;     //防御力
    private int atkScale;       //攻击范围
    private Camp camp;          //阵营，防止自相残杀

    //攻击图片
    Image imageAtk;

    //线程相关
    private boolean Living;    //是否存活
    public static Maps<Creature> maps;   //static变量，所有的生物共享一个maps

    public Creature(String name) {
        super(name);
        this.Living = true;
        this.HP_All = HP_Creature;
        this.HP_Remain = HP_All;
        this.ATK = ATK_Creature;
        this.DEF = DEF_Creature;
        this.atkScale = Scale_Creature;
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
            int p = rand.nextInt(5);
            if(p<2) //不一定严格寻找
            {
                Direction direct = Direction.UP;
                int offX = 0;
                int offY = 0;
                synchronized (maps){
                    direct = maps.getEnemyDirection(location.getX(),location.getY());
                    System.out.println(this.toString()+" "+direct);
                    switch (direct){
                        case UP:    offY=-1;break;
                        case DOWN:  offY=1;break;
                        case LEFT:  offX=-1;break;
                        case RIGHT: offX=1; break;
                    }
                }
                newX = location.getX() + offX;
                newY = location.getY() + offY;
            }else {
                newX = location.getX() + rand.nextInt(3)-1;
                newY = location.getY() + rand.nextInt(3)-1;
            }

            while (!insideMaps(newX,newY)){
                newX = location.getX() + rand.nextInt(3)-1;
                newY = location.getY() + rand.nextInt(3)-1;
            }
        }catch (NullPointerException e){
            System.out.println(this.getClass().getSimpleName());
        }

        synchronized (maps){
            if(!maps.empty(newX,newY)){
                if(maps.getContent(newX,newY).camp==Camp.DEAD){
                    newX = location.getX() + rand.nextInt(3)-1;
                    newY = location.getY() + rand.nextInt(3)-1;
                }
            }
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
//            String musicfile = "";
//            switch (maps.getContent(x,y).getCamp()){
//                case JUSTICE: musicfile = this.getClass().getClassLoader().getResource("media/weapons.mp3").toString();break;
//                case EVIL:musicfile = this.getClass().getClassLoader().getResource("media/damage1.mp3").toString();break;
//                case DEAD:return;
//            }
//
//            Media media2 = new Media(musicfile);
//            MediaPlayer mp2 = new MediaPlayer(media2);
//            mp2.play();
            double hp = maps.getContent(x,y).getHP_Remain();
            maps.drawAtk(this,location.getX(),location.getY(),x,y);
            hp -= 30*this.getATK()/maps.getContent(x,y).getDEF();
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
                for(int i=-atkScale;i<=atkScale;i++){
                    for(int j=-atkScale;j<=atkScale;j++){
                        if(i==0 && j==0){
                            //自己杀自己，不行的
                        }else{
                            int xx = location.getX()+i;
                            int yy = location.getY()+j;
                            if(insideMaps(xx,yy)){
                                synchronized (maps){
                                    if(maps.getContent(xx,yy)!=null){
                                        if(maps.getContent(xx,yy).getCamp()!=this.camp && maps.getContent(xx,yy).getCamp()!=Camp.DEAD){
                                            Fighting(xx,yy);
                                            i=atkScale;    //一次只能攻击一次
                                            j=atkScale;    //一次只能攻击一次
                                        }
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

    public Image getImageAtk() {
        return imageAtk;
    }

    public double getATK() {
        return ATK;
    }

    public void setATK(double ATK) {
        this.ATK = ATK;
    }

    public double getDEF() {
        return DEF;
    }

    public void setDEF(double DEF) {
        this.DEF = DEF;
    }

    public int getAtkScale() {
        return atkScale;
    }

    public void setAtkScale(int atkScale) {
        this.atkScale = atkScale;
    }
}

