package calabashBrothers.GUI;

import calabashBrothers.beings.*;
import calabashBrothers.formation.*;

import java.util.ArrayList;
import java.util.Random;


public class God {

    private static final int row = 15;
    private static final int col = 15;
    private static final int size = 50;

    private static <T>void SetRandom(ArrayList<T> list){
        Random r = new Random();
        int times = r.nextInt(20);
        int m = 0;
        int n = 0;
        T tmp;
        for (int i = 0; i < times ; i++) {
            m = r.nextInt(7);
            n = r.nextInt(7);
            tmp = list.get(m);
            list.set(m,list.get(n));
            list.set(n,tmp);
        }
    }

    private static void battleSleep(int sleepTime){
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @SuppressWarnings("unchecked") //maps会警告，就用后来学的注解了
    public  void characterInterface() {

        ArrayList<Creature> boys = new ArrayList<Creature>();       //7个葫芦娃
        ArrayList<Creature> mons = new ArrayList<Creature>();       //蝎子精和小喽啰的阵营

        Grandpa gp =new Grandpa();                  //爷爷
        Snake sk = new Snake();                     //蛇精
        Scorpion sp = new Scorpion();               //蝎子精

        for (int i = 0; i < 7; i++) {               //葫芦娃的初始化
            boys.add(new CalabashBoy(i));
        }
        SetRandom(boys);                            //使得葫芦娃乱序

        mons.add(new Scorpion());                   //蝎子精继承于Monster类，因此成为领队
        for(int i=1;i<19;i++){                      //小喽啰的初始化
            mons.add(new Monster());
        }

        Maps maps = new Maps<Creature>(row,col);              //创建一个15*15的地图
        BattleFieldController bf = new BattleFieldController();  //创建一个15*15的地图(GUI)

        ChangShe cs = new ChangShe(4,3);//长蛇阵 葫芦娃阵法
        ChongE ce = new ChongE(4,3);    //衝轭阵 葫芦娃阵法
        HeYi hy= new HeYi(7,4);         //鹤翼阵 葫芦娃阵法
        YanXing yx = new YanXing(4,6); //雁行阵 葫芦娃阵法

        FangYuan fy =new FangYuan(7,7); //方圆阵 妖怪阵法
        FengShi fs= new FengShi(7,7);   //锋矢阵 妖怪阵法
        YuLin yl= new YuLin(7,7);       //鱼鳞阵 妖怪阵法
        YanYue yy= new YanYue(7,7);     //偃月阵 妖怪阵法

        //Round 1： 长蛇 vs 偃月
        cs.SetFormation(maps,boys,0);
        yy.SetFormation(maps,mons,0);
        gp.CheeringUp(maps,7,1);
        sk.CheeringUp(maps,7,13);
        maps.showMaps();
        maps.removeMaps();
        battleSleep(1000);

        //Round 2： 长蛇 vs 鱼鳞
        cs.SetFormation(maps,boys,0);
        yl.SetFormation(maps,mons,0);
        gp.CheeringUp(maps,7,1);
        sk.CheeringUp(maps,7,13);
        maps.showMaps();
        maps.removeMaps();
        battleSleep(1000);

        //Round 3： 衝轭 vs 鱼鳞
        ce.SetFormation(maps,boys,0);
        yl.SetFormation(maps,mons,0);
        gp.CheeringUp(maps,7,1);
        sk.CheeringUp(maps,7,13);
        maps.showMaps();
        maps.removeMaps();
        battleSleep(1000);

        //Round 4： 鹤翼 vs 锋矢
        hy.SetFormation(maps,boys,0);
        fs.SetFormation(maps,mons,0);
        gp.CheeringUp(maps,7,1);
        sk.CheeringUp(maps,7,13);
        maps.showMaps();
        maps.removeMaps();
        battleSleep(1000);

        //Round 4： 雁行 vs 方円
        yx.SetFormation(maps,boys,0);
        fy.SetFormation(maps,mons,0);
        gp.CheeringUp(maps,7,1);
        sk.CheeringUp(maps,7,10);
        maps.showMaps();
        maps.removeMaps();
        battleSleep(1200);


    }
}