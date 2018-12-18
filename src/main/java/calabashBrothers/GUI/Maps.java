package calabashBrothers.GUI;

import calabashBrothers.beings.Creature;
import calabashBrothers.beings.enums.Camp;
import calabashBrothers.beings.enums.Direction;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @ Author     ：Young
 * @ Description：战场的地图，二维平面，每个点是一个Unit
 */
public class Maps<T extends Creature> implements Config{
    private int rows;
    private int cols;
    private ArrayList<ArrayList<unit<T>>> maps; //用容器表示二维数组，再加上泛型的unit，有点复杂感觉

    private static Canvas battleFiledCanvas;
    private static GraphicsContext gc;     //2D画布
    private static double canvasHeight;   //画布的高度
    private static double canvasWidth;    //画布的宽度
    private static double UnitSize;       //单位宽度

    private int justiceCounts;
    private int evilCounts;
    private boolean startFlag;

    public static Canvas getBattleFiledCanvas() {
        return battleFiledCanvas;
    }

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

    public Maps(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        maps = new ArrayList<ArrayList<unit<T>>>();
        for (int i = 0; i <rows ; i++) {
            maps.add(new ArrayList<unit<T>>()); //增加一行
            for (int j = 0; j <cols ; j++) { //增加一列
                maps.get(i).add(new unit<T>(i,j));
            }
        }
        this.justiceCounts = 100;
        this.evilCounts = 100;
        this.startFlag  = false;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public  T getContent(int x,int y){
        return maps.get(x).get(y).getContent();
    }

    public boolean empty(int x,int y){
        return  maps.get(x).get(y).none();
    }

    synchronized public  void setContent(int x,int y,T content){
        maps.get(x).get(y).setContent(content);
        if(content != null){
            content.setLocation(new Coordinate(x,y));
        }
    }

    public ArrayList<ArrayList<unit<T>>> getMaps() {
        return maps;
    }

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

    private void displaySleep(int ms){
        try{
            TimeUnit.MILLISECONDS.sleep(ms);
        }catch (InterruptedException e){
            System.out.println(e.toString());
        }
    }

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
                T tmp = maps.get(i).get(j).getContent();
                if( tmp != null){
                    gc.drawImage(tmp.getImage(),j*UnitSize,i*UnitSize,UnitSize,UnitSize);
                    if(tmp.getCamp()!= Camp.DEAD){
                        gc.setStroke(Color.RED);
                        gc.setLineWidth(5);
                        gc.strokeLine(j*UnitSize, i*UnitSize, (j+1)*UnitSize, i*UnitSize);
                        gc.setStroke(Color.CHARTREUSE);
                        gc.strokeLine(j*UnitSize, i*UnitSize, (j+tmp.getHP_Remain()/tmp.getHP_All())*UnitSize, i*UnitSize);
                    }

                    if(!startFlag){
                        notNullCnts++;
                    }

                    if(tmp.getCamp() == Camp.JUSTICE){
                        justiceCounts++;
                    }else if(tmp.getCamp() == Camp.EVIL){
                        evilCounts++;
                    }


                    switch (tmp.getClass().getSimpleName()){
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

    }

    public synchronized void refreshMaps(){
        gc.clearRect(0,0,canvasWidth,canvasHeight);//每次刷新时删除
    }

    public void removeMaps(){
        for (int i = 0; i <rows ; i++) {
            for (int j = 0; j <cols ; j++) {
                maps.get(i).get(j).removeContent();
            }
        }
    }

    ArrayList<T> getLives(){
        ArrayList<T> res = new ArrayList<>();
        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                synchronized (maps){
                    T tmp = maps.get(i).get(j).getContent();
                    if(tmp!=null){
                        res.add(tmp);
                    }
                }
            }
        }
        return  res;
    }

    //得到maps中的存活的两方数量， TODO:可优化，给maps一个计数变量
    ArrayList<Integer> getCounts(){
        ArrayList<Integer> res = new ArrayList<Integer>();
        int justiceCnts = 0;
        int evilCnts = 0;
        for(int i=0;i<rows;i++){
            for(int j=0;j<cols;j++){
                synchronized (maps){
                    if(maps.get(i).get(j).getContent()!=null){
                        if(maps.get(i).get(j).getContent().getCamp()==Camp.JUSTICE){
                            justiceCnts++;
                        }else if(maps.get(i).get(j).getContent().getCamp()==Camp.EVIL){
                            evilCnts++;
                        }
                    }
                }
            }
        }
        res.add(justiceCnts);
        res.add(evilCnts);
        return  res;
    }

    public int getJusticeCounts() {
        return justiceCounts;
    }

    public int getEvilCounts() {
        return evilCounts;
    }

    //寻找敌人的函数
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

    //从(fromX,Y) to (x,y)的攻击波，左上角的坐标
    public void drawAtk(T t,int fromX,int fromY,int x,int y,int damagePoints){
        System.out.println("Atk:from"+"("+fromX+","+fromY+") to ("+x+","+y+")");
        double centerFromX = (double) fromX*UnitSize+UnitSize/4; //起始方格中央
        double centerFromY = (double) fromY*UnitSize+UnitSize/4; //起始方格中央
        double centerTargetX = (double) x*UnitSize+UnitSize/4;   //目标方格中央
        double centerTargetY = (double) y*UnitSize+UnitSize/4;   //目标方格中央
        double centerX = (centerFromX+centerTargetX)/2; //中间方格中央
        double centerY = (centerFromY+centerTargetY)/2; //中间方格中央

        //攻击瞄准线
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);
        gc.strokeLine(centerFromY+UnitSize/4,centerFromX+UnitSize/4,centerTargetY+UnitSize/4,centerTargetX+UnitSize/4);

        //攻击动画
        double picSize = UnitSize/2;
//        gc.drawImage(t.getImageAtk(),centerFromY,centerFromX,UnitSize/2,UnitSize/2);
        gc.setFill(Color.RED);
//        gc.fillText("HP-"+damagePoints,centerTargetY,centerTargetX,UnitSize*0.75);
        gc.drawImage(t.getImageAtk(),centerTargetY,centerTargetX,picSize,picSize);
        gc.drawImage(t.getImageAtk(),centerY,centerX,picSize,picSize);
//        gc.drawImage(t.getImageAtk(),(centerY+centerFromY)/2,(centerX+centerFromX)/2,picSize,picSize);
        gc.drawImage(t.getImageAtk(),(centerY+centerTargetY)/2,(centerX+centerTargetX)/2,picSize,picSize);

    }

    public void gameOver(Camp winner){
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
    }
}
