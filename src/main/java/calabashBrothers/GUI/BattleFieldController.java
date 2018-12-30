package calabashBrothers.GUI;

import calabashBrothers.GUI.Record.Recorder;
import calabashBrothers.GUI.Record.RecorderSystem;
import calabashBrothers.beings.*;

import calabashBrothers.beings.enums.Direction;
import calabashBrothers.formation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @ Author     ：Young
 * @ Description：JavafxGUI框架交互的控制器
 */
public class BattleFieldController implements Config{

    Maps maps = new Maps<Creature>(Height,Width);   //创建一个棋盘布局的地图

    private double canvasHeight;   //画布的高度
    private double canvasWidth;    //画布的宽度
    private double UnitSize;       //单位宽度

    private int initFormation;     //初始对阵
    private boolean gameStarted;


    //生物类
    ArrayList<Creature> boys;    //7个葫芦娃
    ArrayList<Creature> mons;    //蝎子精和小喽啰的阵营
    Grandpa gp;                  //爷爷
    Snake sk;                    //蛇精
    Scorpion sp;                 //蝎子精

    //线程池
    ExecutorService exec;
    boolean gamePause;
    //展示类
    DisplayField player;
    //控制行为
    boolean canvasInitialized = false;//是否被初始化

    @FXML
    private Canvas mainCanvas;      //主画布
    @FXML
    private Button startBotton;     //开始的按钮
    @FXML
    public Label initFormationText;//展示初始阵型的信息
    @FXML
    private BorderPane mainPaneWindow;
    @FXML
    public Label StateLabel;        //模式选择器
    @FXML
    public Slider SpeedSlider;      //速度选择器
    public Slider SpeedCreatureSlider;

    //构造函数
    public BattleFieldController() {
        initCreatures();
        initFormation = 0;
        gameStarted = false;
        gamePause = false;
        exec = Executors.newCachedThreadPool();
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
        canvasInitialized = true;
        //对Creature的画布和Maps里的Canvas初始化（类的静态变量，直接设置）
        Maps.setBattleFiledCanvas(mainCanvas);
        Creature.setMaps(maps);
        player.setWindow(mainPaneWindow.getScene().getWindow());
    }
    //设置阵型时初始化
    private void initCanvasFormation(){
        //对Creature的画布和Maps里的Canvas初始化（类的静态变量，直接设置）
        Maps.setBattleFiledCanvas(mainCanvas);
        Creature.setMaps(maps);
        player.setWindow(mainPaneWindow.getScene().getWindow());
    }

    //初始阵型
    private void Round1(){
        //Round 1： 长蛇 vs 偃月
        FormationFactory.createChangShe(4,3).SetFormation(maps,boys,0);
        FormationFactory.createYanYue(7,7).SetFormation(maps,mons,0);
        gp.CheeringUp(maps,7,1);
        sk.CheeringUp(maps,7,13);
    }
    private void Round2(){
        //Round 2： 长蛇 vs 鱼鳞
        FormationFactory.createChangShe(4,3).SetFormation(maps,boys,0);
        FormationFactory.createYuLin(7,7).SetFormation(maps,mons,0);
        gp.CheeringUp(maps,7,1);
        sk.CheeringUp(maps,7,13);
    }
    private void Round3(){
        //Round 3： 衝轭 vs 鱼鳞
        FormationFactory.createChongE(4,3).SetFormation(maps,boys,0);
        FormationFactory.createYuLin(7,7).SetFormation(maps,mons,0);
        gp.CheeringUp(maps,7,1);
        sk.CheeringUp(maps,7,13);
    }
    private void Round4(){
        //Round 4： 鹤翼 vs 锋矢
        FormationFactory.createHeYi(7,4).SetFormation(maps,boys,0);
        FormationFactory.createFengShi(7,7).SetFormation(maps,mons,0);
        gp.CheeringUp(maps,7,1);
        sk.CheeringUp(maps,7,13);
    }
    private void Round5(){
        //Round 5： 雁行 vs 方円
        FormationFactory.createYanXing(4,6).SetFormation(maps,boys,0);
        FormationFactory.createFangYuan(7,7).SetFormation(maps,mons,0);
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

        if(!gameStarted){
            StateLabel.setText("当前模式：战斗");
            startBotton.setText("重新开始");
            gameStarted = true;
            if(!canvasInitialized){
                initCanvas();
                exec.execute(player);

                ArrayList<Creature> livingList = maps.getLives();
                for(Creature c: livingList){
                    if(c!=null){
                        exec.execute(c);
                    }
                }

//                exec.shutdown();
            }
        }else{
            if(player.isRunning() || player.isReplaying()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("温馨提示");
                alert.setHeaderText(null);
                alert.setContentText("游戏正在进行或正在回放中，请稍后继续");
                alert.showAndWait();
            }else{
                //重新开始需要什么条件呢
                //重置生物和maps的引用
                synchronized (maps){
                    maps.resetMaps();
                    maps.removeMaps();
                    maps.refreshMaps();
                }
                maps = new Maps<Creature>(Height,Width);
                initCreatures();

                switch (initFormation){
                    case 0:formation1(new ActionEvent());break;
                    case 1:formation2(new ActionEvent());break;
                    case 2:formation3(new ActionEvent());break;
                    case 3:formation4(new ActionEvent());break;
                    case 4:formation5(new ActionEvent());break;
                }

                synchronized (player){
                    player.setDisPlaying(false);
                }

                ArrayList<Creature> livingList = maps.getLives();
                for(Creature c: livingList){
                    if(c!=null){
                        exec.execute(c);
                    }
                }
                player = new DisplayField();
                player.setMaps(maps);
                exec.execute(player);


            }

        }
    }
    //暂停/继续
    public void gamePause(ActionEvent actionEvent) {
        if(gameStarted){
            if(!gamePause){
                StateLabel.setText("当前模式：暂停");
                System.out.println("暂停游戏");
                synchronized (maps){
                    maps.resetMaps();
                }
                gamePause = true;
            }else{
                //重新激活线程
                StateLabel.setText("当前模式：战斗");
                ArrayList<Creature> livingList = maps.getLives();
                for(Creature c: livingList){
                    if(c!=null){
                        c.setLiving(true);
                        exec.execute(c);
                    }
                }
                gamePause = false;
            }
        }
    }
    //读取文件复盘
    public void getGameRecord(ActionEvent actionEvent) {

        if(!canvasInitialized){
            initCanvas();
            exec.execute(player);
        }

        synchronized (player){
            if((player.isRunning()&&gameStarted) || player.isReplaying()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("温馨提示");
                alert.setHeaderText(null);
                alert.setContentText("游戏正在战斗中，请待游戏结束后读档回放");
                alert.showAndWait();
                return;
            }
        }

        StateLabel.setText("当前模式：回放");
        System.out.println("读取复盘");
        RecorderSystem rs = new RecorderSystem(mainPaneWindow.getScene().getWindow());
        if(rs==null){
            return;
        }
        ArrayList<Recorder> gameRecords = rs.openRecord();
        player.setRunning(false);
        player.setReplaying(true);
        System.out.println("replay");
        player.setRecorder(gameRecords);
        exec.submit((Callable<String>) player);

    }

    //设置初始阵型
    public void formation1(ActionEvent actionEvent) {
        initFormation = 0;
        initFormationText.setText("长蛇 vs 偃月");
        maps.removeMaps();
        setInitFormations(0);
        initCanvasFormation();
        maps.showMaps();
    }
    public void formation2(ActionEvent actionEvent) {
        initFormation = 1;
        initFormationText.setText("长蛇 vs 鱼鳞");
        maps.removeMaps();
        setInitFormations(1);
        initCanvasFormation();
        maps.showMaps();
    }
    public void formation3(ActionEvent actionEvent) {
        initFormation = 2;
        initFormationText.setText("衝轭 vs 鱼鳞");
        maps.removeMaps();
        setInitFormations(2);
        initCanvasFormation();
        maps.showMaps();
    }
    public void formation4(ActionEvent actionEvent) {
        initFormation = 3;
        initFormationText.setText("鹤翼 vs 锋矢");
        maps.removeMaps();
        setInitFormations(3);
        initCanvasFormation();
        maps.showMaps();
    }
    public void formation5(ActionEvent actionEvent) {
        initFormation = 4;
        initFormationText.setText("雁行 vs 方円");
        maps.removeMaps();
        setInitFormations(4);
        initCanvasFormation();
        maps.showMaps();
    }

    //响应键盘事件
    public void OnKeyPressEvents(KeyEvent keyEvent) {
        //按下空格开始
        if(keyEvent.getCode() == KeyCode.SPACE){
            this.gameStart(new ActionEvent());
            System.out.println("Press Space");
        }
        //按下L读取
        if(keyEvent.getCode() == KeyCode.L){
            this.getGameRecord(new ActionEvent());
            System.out.println("Press L");
        }

        if(keyEvent.getCode() == KeyCode.P){
            this.gamePause(new ActionEvent());
            System.out.println("Press P");
        }

        //操作方向
        if(keyEvent.getCode() == KeyCode.UP){
            System.out.println("Press UP");
            gp.addOptions(Direction.LEFT);
        }
        if(keyEvent.getCode() == KeyCode.DOWN){
            System.out.println("Press DOWN");
            gp.addOptions(Direction.RIGHT);
        }
        if(keyEvent.getCode() == KeyCode.LEFT){
            System.out.println("Press LEFT");
            gp.addOptions(Direction.UP);
        }
        if(keyEvent.getCode() == KeyCode.RIGHT){
            System.out.println("Press RIGHT");
            gp.addOptions(Direction.DOWN);
        }

    }

    //刷新速度选择
    public void dragSpeed(MouseEvent mouseEvent) {
        System.out.print("刷新速度选择");
        double newFlashSpeed = 200+800*(1-SpeedSlider.getValue()/SpeedSlider.getMax());
        System.out.println(newFlashSpeed);
        DisplayField.setDisplayHz(new Double(newFlashSpeed).intValue());
    }

    //生物速度选择
    public void dragCreatureSpeed(MouseEvent mouseEvent) {
        System.out.print("生物速度选择");
        double newFlashSpeed = 400+1600*(1-SpeedCreatureSlider.getValue()/SpeedCreatureSlider.getMax());
        System.out.println(newFlashSpeed);
        Creature.setSleepHZ(new Double(newFlashSpeed).intValue());
    }

    public void showManual(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("\n" +
                "1.游戏开始前可以选择阵型，也可直接开始游戏。\n" +
                "2.游戏进行过程中可以选择暂停和继续并通过左侧滚动条调整游戏速度和屏幕刷新频率。\n" +
                "3.游戏结束后系统会自动存档。可以在游戏开始前或者结束后选择读档。\n" +
                "3.开始游戏快捷键为“空格”；读档快捷键为“L”；暂停/继续快捷键为“P”。\n" +
                "4.小彩蛋：战斗时可以试着用方向键控制爷爷的行动，有小惊喜哦。\n" +
                "\n");
        alert.showAndWait();
    }

    public void showAbout(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("关于本程序");
        alert.setHeaderText(null);
        alert.setContentText("葫芦娃大战妖精是一个简易小游戏，是南京大学计科《Java程序设计》课程的大作业。\n" +
                "该程序使用Java8和Javafx的GUI框架开发，用Maven进行构建管理。游戏基于动画《葫芦娃》的故事，模拟了葫芦娃大战妖精的场景\n\n" +
                "                              161220096 欧阳鸿荣\n");
        alert.showAndWait();
    }
}
