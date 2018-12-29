package calabashBrothers.GUI;

import calabashBrothers.GUI.Record.*;
import calabashBrothers.beings.Creature;
import calabashBrothers.beings.enums.Camp;
import calabashBrothers.beings.enums.Direction;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Window;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;


/**
 * @ Author     ：Young
 * @ Description：战场的地图，二维平面，每个点是一个Unit
 */
public class Maps<T extends Creature> implements Config{
    private int rows;
    private int cols;
    private ArrayList<ArrayList<unit<Creature>>> maps; //用容器表示二维数组，再加上泛型的unit，有点复杂感觉

    //图形化的一些变量
    private static Canvas battleFiledCanvas; //主界面的Canvas
    private static GraphicsContext gc;       //2D画布
    private static double canvasHeight;     //画布的高度
    private static double canvasWidth;      //画布的宽度
    private static double UnitSize;         //单位宽度

    //游戏的一些变量
    private int justiceCounts;  //正方数目
    private int evilCounts;     //反方数目
    private boolean startFlag;  //游戏是否开始

    //记录变量
    private ArrayList<Recorder> recordList = new ArrayList<>();     //用来保存战斗记录的List
    private Recorder recorder = new Recorder();                     //用来保存每回合的记录
//    private ArrayList<AtkRecord> atkList = new ArrayList<>();       //保存每回合的战斗记录
    CreatureMap recordMaps = new CreatureMap(); //映射器


    //构造函数
    public Maps(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        maps = new ArrayList<ArrayList<unit<Creature>>>();
        for (int i = 0; i <rows ; i++) {
            maps.add(new ArrayList<unit<Creature>>()); //增加一行
            for (int j = 0; j <cols ; j++) { //增加一列
                maps.get(i).add(new unit<Creature>(i,j));
            }
        }
        this.justiceCounts = 100;
        this.evilCounts = 100;
        this.startFlag  = false;
    }

    //得到画布
    public static Canvas getBattleFiledCanvas() {
        return battleFiledCanvas;
    }

    //设置画布
    public static void setBattleFiledCanvas(Canvas battleFiledCanvas) {
        Maps.battleFiledCanvas = battleFiledCanvas;
        //基本参数
        Maps.canvasHeight = battleFiledCanvas.getHeight();
        Maps.canvasWidth = battleFiledCanvas.getWidth();
        Maps.UnitSize = canvasHeight/Height;
        assert(UnitSize != canvasWidth/Width);
        System.out.println("Canvas:Heitht="+canvasHeight+",Width="+canvasWidth);
        gc = battleFiledCanvas.getGraphicsContext2D();
    }

    //得到Rows
    public int getRows() {
        return rows;
    }

    //得到Cols
    public int getCols() {
        return cols;
    }

    //得到某处的生物
    public  Creature getContent(int x,int y){
        return maps.get(x).get(y).getContent();
    }

    //判断某处是否为null
    public boolean empty(int x,int y){
        return  maps.get(x).get(y).none();
    }

    //在x,y上放置生物
    synchronized public  void setContent(int x,int y,Creature content){
        maps.get(x).get(y).setContent(content);
        if(content != null){
            content.setLocation(new Coordinate(x,y));
        }
    }

    //得到maps
    public ArrayList<ArrayList<unit<Creature>>> getMaps() {
        return maps;
    }

    //绘制战场背景和方格
    private void drawBoradLines(){

        gc.setFill(Color.rgb(255,255,255,0.4));
        gc.fillRect(0,0,canvasWidth,canvasWidth);

        gc.setLineWidth(1);
        gc.setStroke(Color.BLACK);
        //绘制N*N的边界线
        for(int i=1;i<Height;i++){
            gc.strokeLine(i*UnitSize,0,i*UnitSize,canvasWidth);
        }

        for(int i=1;i<Width;i++){
            gc.strokeLine(0,i*UnitSize,canvasHeight,i*UnitSize);
        }
    }

    //封装的Sleep函数
    private void displaySleep(int ms){
        try{
            TimeUnit.MILLISECONDS.sleep(ms);
        }catch (InterruptedException e){
            System.out.println(e.toString());
        }
    }

    //对于一帧的记录文件，打印文件内容
    synchronized public void replayMaps(Recorder rc){
        this.removeMaps();
        //画方格里的对象
        Iterator<ObjectRecord> itObj = rc.getObjList().iterator();
        while(itObj.hasNext()){
            ObjectRecord obj = itObj.next();
            int setX = obj.getPos().getX();
            int setY = obj.getPos().getY();
            Creature tmp = recordMaps.getClassByString(obj.getObject());
            this.setContent(obj.getPos().getX(),obj.getPos().getY(), tmp);//不安全
            if(tmp.getCamp()!=Camp.DEAD){
                this.getContent(setX,setY).setHP_Remain(obj.getHP_Remain());
            }
        }
        showMaps();
        //画攻击
        Iterator<AtkRecord> itAtk = rc.getAtkList().iterator();
        while(itAtk.hasNext()){
            AtkRecord atk = itAtk.next();
            Creature tmp = recordMaps.getClassByString(atk.getAttaker());
            int fromX = atk.getAtkFrom().getX();
            int fromY = atk.getAtkFrom().getY();
            int x = atk.getAtkTo().getX();
            int y = atk.getAtkTo().getY();
            this.drawAtk(tmp,fromX,fromY,x,y,0);
        }
    }

    //打印console里的字符
    public synchronized void showConsoleMaps(String types){
        switch (types){
            case "CalabashBoy":{
                System.out.print("C");
            }break;

            case "Grandpa":{
                System.out.print("G");
            }break;

            case "Monster":{
                System.out.print("M");
            }break;

            case "Scorpion": {
                System.out.print("L");
            }break;

            case"Snake":{
                System.out.print("S");
            }break;
            case "DeathObject":{
                System.out.print("D");
            }break;
            default:{
                System.out.println("Error when showMaps");
            }

        }
    }

    //打印战场实况，一个很重要的函数
    public synchronized void showMaps(){
        gc.clearRect(0,0,canvasWidth,canvasHeight);//每次刷新时删除
        drawBoradLines();
        if(startFlag){
            this.justiceCounts = 0;
            this.evilCounts = 0;
        }
        int notNullCnts=0;


        for (int i = 0; i <rows ; i++) {
            for (int j = 0; j <cols ; j++) {
                Creature tmp = maps.get(i).get(j).getContent();
                if( tmp != null){

                    //保存生物信息
                    recorder.addObjList(new ObjectRecord(tmp.toString(),tmp.getHP_Remain(),tmp.getLocation()));
                    //画生物图片
                    gc.drawImage(tmp.getImage(),j*UnitSize,i*UnitSize,UnitSize,UnitSize);
                    //画血量条
                    if(tmp.getCamp()!= Camp.DEAD){
                        gc.setStroke(Color.RED);
                        gc.setLineWidth(5);
                        gc.strokeLine(j*UnitSize, i*UnitSize, (j+1)*UnitSize, i*UnitSize);
                        gc.setStroke(Color.CHARTREUSE);
                        gc.strokeLine(j*UnitSize, i*UnitSize, (j+tmp.getHP_Remain()/tmp.getHP_All())*UnitSize, i*UnitSize);
                    }

                    //一些额外处理
                    if(!startFlag){
                        notNullCnts++;
                    }

                    //计算人数
                    if(tmp.getCamp() == Camp.JUSTICE){
                        justiceCounts++;
                    }else if(tmp.getCamp() == Camp.EVIL){
                        evilCounts++;
                    }

                    showConsoleMaps(tmp.getClass().getSimpleName());

                }else{
                    System.out.print("-");
                }
            }
            System.out.println(" ");
        }
        System.out.println(" ");
        System.out.println(" ");
        if(notNullCnts > 14){
            startFlag = true;
        }
        System.out.println("刷新：justiceCounts:"+justiceCounts);
        System.out.println("刷新：evilCounts:"+evilCounts);
        recordList.add(recorder);
        recorder = new Recorder();

    }

    //清空画布内容
    public synchronized void refreshMaps(){
        gc.clearRect(0,0,canvasWidth,canvasHeight);//每次刷新时删除
    }

    //删除当前地图所有的对象
    public void removeMaps(){
        for (int i = 0; i <rows ; i++) {
            for (int j = 0; j <cols ; j++) {
                maps.get(i).get(j).removeContent();
            }
        }
    }

    //得到战场里所有的生物
    ArrayList<Creature> getLives(){
        ArrayList<Creature> res = new ArrayList<>();
        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                synchronized (maps){
                    Creature tmp = maps.get(i).get(j).getContent();
                    if(tmp!=null){
                        res.add(tmp);
                    }
                }
            }
        }
        return  res;
    }

    //得到正方阵营的数量
    public int getJusticeCounts() {
        return justiceCounts;
    }

    //得到反方阵营的数量
    public int getEvilCounts() {
        return evilCounts;
    }

    //自动寻找敌人方向
    public Direction getEnemyDirection(int centerX, int centerY){
        Camp myCemp = maps.get(centerX).get(centerY).getContent().getCamp();
        //上下左右扫描
        int upCnts = 0;
        int downCnts = 0;
        int leftCnts = 0;
        int rightCnts = 0;
        synchronized (maps){
            //i在画布上代表横坐标，j代表纵坐标
            for(int i=0;i<rows;i++){
                for(int j=0;j<cols;j++){
                    if(maps.get(i).get(j).getContent()!=null){
                        if(maps.get(i).get(j).getContent().getCamp()!=Camp.DEAD){
                            //此时只能是JUSTICE OR EVIL
                            if(myCemp!=maps.get(i).get(j).getContent().getCamp()){
                                if(centerX<i){
                                    rightCnts++;
                                }else if(centerX>i){
                                    leftCnts++;
                                }
                                if(centerY<j){
                                    downCnts++;
                                }else if(centerY>j){
                                    upCnts++;
                                }
                            }
                        }
                    }
                }
            }
        }
        Direction res = Direction.UP;
        int maxCnts = upCnts;
        if(downCnts>maxCnts){
            res = Direction.DOWN;
            maxCnts = downCnts;
        }
        if(leftCnts>maxCnts){
            res = Direction.LEFT;
            maxCnts = leftCnts;
        }
        if(rightCnts>maxCnts){
            res = Direction.RIGHT;
            maxCnts = rightCnts;
        }
        return  res;
    }

    //增加一条攻击记录
    public void addAtkRecord(Creature t,int fromX,int fromY,int x,int y,int damagePoints){
        System.out.println("记录了：Atk:from"+"("+fromX+","+fromY+") to ("+x+","+y+")");
        recorder.addAtkList(new AtkRecord(t.toString(),new Coordinate(fromX,fromY),new Coordinate(x,y)));
    }


    //从(fromX,Y) to (x,y)的攻击波，左上角的坐标
    public void drawAtk(Creature t,int fromX,int fromY,int x,int y,int damagePoints){

        addAtkRecord(t,fromX,fromY,x,y,damagePoints);

        System.out.println("Atk:from"+"("+fromX+","+fromY+") to ("+x+","+y+")");
        double centerFromX = (double) fromX*UnitSize+UnitSize/4; //起始方格中央
        double centerFromY = (double) fromY*UnitSize+UnitSize/4; //起始方格中央
        double centerTargetX = (double) x*UnitSize+UnitSize/4;   //目标方格中央
        double centerTargetY = (double) y*UnitSize+UnitSize/4;   //目标方格中央
        double centerX = (centerFromX+centerTargetX)/2; //中间方格中央
        double centerY = (centerFromY+centerTargetY)/2; //中间方格中央

        //攻击瞄准线
//        gc.setStroke(Color.WHITE);
//        gc.setLineWidth(2);
//        gc.strokeLine(centerFromY+UnitSize/4,centerFromX+UnitSize/4,centerTargetY+UnitSize/4,centerTargetX+UnitSize/4);

        //攻击动画
        double picSize = UnitSize/2;
//        gc.drawImage(t.getImageAtk(),centerFromY,centerFromX,UnitSize/2,UnitSize/2);
        gc.setFill(Color.RED);
//        gc.fillText("HP-"+damagePoints,centerTargetY,centerTargetX,UnitSize*0.75);
        gc.drawImage(t.getImageAtk(),centerTargetY,centerTargetX,picSize,picSize);
//        gc.drawImage(t.getImageAtk(),centerY,centerX,picSize,picSize);
//        gc.drawImage(t.getImageAtk(),(centerY+centerFromY)/2,(centerX+centerFromX)/2,picSize,picSize);
        gc.drawImage(t.getImageAtk(),(centerY+centerTargetY)/2,(centerX+centerTargetX)/2,picSize,picSize);

        String musicfile = "";
        switch (t.getCamp()){
            case JUSTICE: musicfile = this.getClass().getClassLoader().getResource("media/weapons.mp3").toString();break;
            case EVIL:musicfile = this.getClass().getClassLoader().getResource("media/damage1.mp3").toString();break;
            case DEAD:return;
        }

        Media media2 = new Media(musicfile);
        MediaPlayer mp2 = new MediaPlayer(media2);
        mp2.play();

    }

    //游戏结束，同时打印结束图片和播放结束音乐
    public void gameOver(Camp winner,Window window){
        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                synchronized (maps){
                    if(maps.get(i).get(j).getContent()!=null){
                        if(maps.get(i).get(j).getContent().getCamp()!=Camp.DEAD){
                            maps.get(i).get(j).getContent().setLiving(false);
                        }
                    }
                }
            }
        }
        String winPath =this.getClass().getClassLoader().getResource("pic/JustiveWinner.jpg").toString();
        if(winner == Camp.EVIL)
            winPath=this.getClass().getClassLoader().getResource("pic/EvilWinner.jpg").toString();
        gc.drawImage(new Image(winPath),0,0,canvasWidth,canvasHeight);
        RecorderSystem rs = new RecorderSystem(window);
        rs.saveRecord(recordList);
    }

    //得到游戏记录列表
    public ArrayList<Recorder> getRecordList() {
        return recordList;
    }

    //重设
    public boolean resetMaps(){
        synchronized (maps){
            for(Creature c: this.getLives()){
                c.setLiving(false);
            }
        }
        return true;
    }
}
