package calabashBrothers.GUI;

import calabashBrothers.beings.Creature;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

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

    public void drawBoradLines(){

        gc.setLineWidth(5);

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
//        drawBoradLines();
        for (int i = 0; i <rows ; i++) {
            for (int j = 0; j <cols ; j++) {
                T tmp = maps.get(i).get(j).getContent();
                if( tmp != null){
                    gc.drawImage(new Image(tmp.getFilePath().toString()),j*UnitSize,i*UnitSize,UnitSize,UnitSize);
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
}
