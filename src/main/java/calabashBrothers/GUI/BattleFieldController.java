package calabashBrothers.GUI;

import calabashBrothers.beings.*;

import calabashBrothers.formation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * @ Author     ：Young
 * @ Description：用于展示战场
 */
public class BattleFieldController implements Config{

    Maps maps = new Maps<Creature>(Height,Width);   //创建一个棋盘布局的地图

    private double canvasHeight;   //画布的高度
    private double canvasWidth;    //画布的宽度
    private double UnitSize;       //单位宽度

    private int initFormation;     //初始对阵

    //可以写一个阵型工厂类
    ChangShe cs = new ChangShe(4,3);//长蛇阵 葫芦娃阵法
    ChongE ce = new ChongE(4,3);    //衝轭阵 葫芦娃阵法
    HeYi hy= new HeYi(7,4);         //鹤翼阵 葫芦娃阵法
    YanXing yx = new YanXing(4,6);  //雁行阵 葫芦娃阵法
    FangYuan fy =new FangYuan(7,7); //方圆阵 妖怪阵法
    FengShi fs= new FengShi(7,7);   //锋矢阵 妖怪阵法
    YuLin yl= new YuLin(7,7);       //鱼鳞阵 妖怪阵法
    YanYue yy= new YanYue(7,7);     //偃月阵 妖怪阵法

    //生物类
    ArrayList<Creature> boys;    //7个葫芦娃
    ArrayList<Creature> mons;    //蝎子精和小喽啰的阵营
    Grandpa gp;                  //爷爷
    Snake sk;                    //蛇精
    Scorpion sp;                 //蝎子精

    //展示类
    DisplayField player;

    @FXML
    private Canvas mainCanvas;      //主画布
    @FXML
    private Button startBotton;     //开始的按钮
    @FXML
    public Label initFormationText;//展示初始阵型的信息

    public BattleFieldController() {
        initCreatures();
        initFormation = 1;
        setInitFormations(initFormation);
        player = new DisplayField();
        player.setMaps(maps);
    }

    //初始化生物
    private void initCreatures(){
        boys = new ArrayList<Creature>();       //7个葫芦娃
        mons = new ArrayList<Creature>();       //蝎子精和小喽啰的阵营
        gp =new Grandpa();                  //爷爷
        sk = new Snake();                     //蛇精
        sp = new Scorpion();               //蝎子精

        for(int i = 0; i < 7; ++i) {
            boys.add(new CalabashBoy(i));
        }
        mons.add(sp);
        for(int i = 1; i < 19; ++i) {
            mons.add(new Monster());
        }

    }
    //初始化画布信息
    private void initCanvas(){
        //对Creature的画布和Maps里的Canvas初始化（类的静态变量，直接设置）
        Maps.setBattleFiledCanvas(mainCanvas);
        Creature.setMaps(maps);
    }

    //初始阵型
    private void Round1(){
        //Round 1： 长蛇 vs 偃月
        cs.SetFormation(maps,boys,0);
        yy.SetFormation(maps,mons,0);
        gp.CheeringUp(maps,7,1);
        sk.CheeringUp(maps,7,13);
    }
    private void Round2(){
        //Round 2： 长蛇 vs 鱼鳞
        cs.SetFormation(maps,boys,0);
        yl.SetFormation(maps,mons,0);
        gp.CheeringUp(maps,7,1);
        sk.CheeringUp(maps,7,13);
    }
    private void Round3(){
        //Round 3： 衝轭 vs 鱼鳞
        ce.SetFormation(maps,boys,0);
        yl.SetFormation(maps,mons,0);
        gp.CheeringUp(maps,7,1);
        sk.CheeringUp(maps,7,13);
    }
    private void Round4(){
        //Round 4： 鹤翼 vs 锋矢
        hy.SetFormation(maps,boys,0);
        fs.SetFormation(maps,mons,0);
        gp.CheeringUp(maps,7,1);
        sk.CheeringUp(maps,7,13);
    }
    private void Round5(){
        //Round 5： 雁行 vs 方円
        yx.SetFormation(maps,boys,0);
        fy.SetFormation(maps,mons,0);
        gp.CheeringUp(maps,7,1);
        sk.CheeringUp(maps,7,10);
    }

    //第一回合，摆阵
    private void setInitFormations(int i) {
        switch (i){
            case 0:Round1();break;
            case 1:Round2();break;
            case 2:Round3();break;
            case 3:Round4();break;
            case 4:Round5();break;
            default:
                System.out.println("执行了错误回合");
        }
    }

    //开始游戏
    public void gameStart(ActionEvent actionEvent) {
        initCanvas();
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(player);

        ArrayList<Creature> livingList = maps.getLives();
        for(Creature c: livingList){
            if(c!=null){
                exec.execute(c);
            }
        }
        exec.shutdown();
    }

    public void formation1(ActionEvent actionEvent) {
        initFormation = 0;
        initFormationText.setText("长蛇 vs 偃月");
        maps.removeMaps();
        setInitFormations(0);
    }

    public void formation2(ActionEvent actionEvent) {
        initFormation = 1;
        initFormationText.setText("长蛇 vs 鱼鳞");
        maps.removeMaps();
        setInitFormations(1);
    }

    public void formation3(ActionEvent actionEvent) {
        initFormation = 2;
        initFormationText.setText("衝轭 vs 鱼鳞");
        maps.removeMaps();
        setInitFormations(2);
    }

    public void formation4(ActionEvent actionEvent) {
        initFormation = 3;
        initFormationText.setText("鹤翼 vs 锋矢");
        maps.removeMaps();
        setInitFormations(3);
    }

    public void formation5(ActionEvent actionEvent) {
        initFormation = 4;
        initFormationText.setText("雁行 vs 方円");
        maps.removeMaps();
        setInitFormations(4);
    }
}
