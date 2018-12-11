package calabashBrothers.GUI;

import calabashBrothers.beings.*;
import calabashBrothers.beings.enums.CalabashName;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.shape.ArcType;
import javafx.scene.paint.Color;

import java.util.ArrayList;


/**
 * @ Author     ：Young
 * @ Description：用于展示战场
 */
public class BattleFieldController {

    final private double Height = 12;  //12 = 720 / 60 表示12*12的网格
    final private double Width = 12;   //12 = 720 / 60

    private double canvasHeight;   //画布的高度 目前是720
    private double canvasWidth;    //画布的宽度 目前是720
    private double UnitSize;       //单位宽度

    @FXML
    private Canvas mainCanvas;      //主画布

    @FXML
    private Button startBotton;     //开始的按钮

    private GraphicsContext gc;

    public BattleFieldController() {
        //
    }

    private void initCanvas(){
        //基本参数
        this.canvasHeight = mainCanvas.getHeight();
        this.canvasWidth = mainCanvas.getWidth();
        this.UnitSize = canvasHeight/Height;
        assert(UnitSize != canvasWidth/Width);

        System.out.println("Canvas:Heitht="+canvasHeight+",Width="+canvasWidth);
        //画布
        gc = this.mainCanvas.getGraphicsContext2D();
    }

    private void drawShapes(GraphicsContext gc) {
        System.out.println("drawShapes");
        gc.setFill(Color.WHITE);
        gc.fillRect(0,0,canvasWidth,canvasHeight);
        gc.setFill(Color.BLACK);
        gc.setLineWidth(5);

        //绘制N*N的边界线
        for(int i=1;i<Height;i++){
            gc.strokeLine(i*UnitSize,0,i*UnitSize,canvasWidth);
        }

        for(int i=1;i<Width;i++){
            gc.strokeLine(0,i*UnitSize,canvasHeight,i*UnitSize);
        }

        ArrayList<Creature> boys = new ArrayList();
        ArrayList<Creature> mons = new ArrayList();
        Grandpa gp = new Grandpa();
        Snake sk = new Snake();
        new Scorpion();


        for(int i = 0; i < 7; ++i) {
            boys.add(new CalabashBoy(i));
        }

//        SetRandom(boys);
        mons.add(new Scorpion());

        for(int i = 1; i < 19; ++i) {
            mons.add(new Monster());
        }

        for(int i=0;i<Width;i++){
            for(int j=0;j<Height;j++){
                gc.drawImage(new Image(boys.get((i+j)%7).getFilePath().toString()),i*UnitSize,j*UnitSize,60,60);
            }
        }
    }

    //开始游戏
    public void gameStart(ActionEvent actionEvent) {

        initCanvas();
        drawShapes(gc);

    }
}
