package calabashBrothers.beings;

import calabashBrothers.GUI.Coordinate;
import calabashBrothers.GUI.Maps;
import calabashBrothers.beings.enums.Camp;
import calabashBrothers.beings.enums.Direction;
import javafx.scene.image.Image;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;

/**
 * @ Author     ：Young
 * @ Description：葫芦娃爷爷
 */
public class Grandpa extends Creature implements CheeringUp,Curable{

    private Queue<Direction> options = new ArrayDeque<>();
    public Grandpa() {
        super("葫芦娃爷爷");
        this.filePath = this.getClass().getClassLoader().getResource("pic/grandpa.jpg");
        image = new Image(filePath.toString());
        imageAtk = new Image(this.getClass().getClassLoader().getResource("pic/leaf.png").toString());
        setCamp(Camp.JUSTICE);  //阵营
        //设置属性
        setHP_All(HP_Grandpa);
        setHP_Remain(HP_Grandpa);
        setATK(ATK_Grandpa);
        setDEF(DEF_Grandpa);
        setAtkScale(Scale_Grandpa);
    }

    @Override
    public void selfIntroduction() {
        super.selfIntroduction();
        System.out.println("我是"+this.name);
    }

    @Override
    public void CheeringUp(Maps<Creature> maps, int x, int y) {
        if(maps.empty(x,y)){
            maps.setContent(x,y,this);
        }
    }

    @Override
    public synchronized void moveRandom() {
        if(options.isEmpty()){
            super.moveRandom();
        }else{
            //如果当前指令队列里有指令的话
            int offX = 0;
            int offY = 0;
            System.out.println("出队前，指令数量为"+options.size());
            Direction direct = options.remove();
            System.out.println("出队后，指令数量为"+options.size());
            switch (direct){
                case UP:    offY=-1;break;
                case DOWN:  offY=1;break;
                case LEFT:  offX=-1;break;
                case RIGHT: offX=1; break;
                default:
                    ;
            }
            int newX = location.getX() + offX;
            int newY = location.getY() + offY;
            if(insideMaps(newX,newY)){
                synchronized (maps){
                    if(!maps.empty(newX,newY)){
                        if(maps.getContent(newX,newY).camp==Camp.DEAD){
                            newX = location.getX() + new Random(newX).nextInt(3)-1;
                            newY = location.getY() + new Random(newY).nextInt(3)-1;
                        }
                    }
                    if(maps.empty(newX,newY)){
                        maps.setContent(location.getX(),location.getY(),null);
                        location = new Coordinate(newX,newY);
                        maps.setContent(newX,newY,this);
                    }
                }
            }
        }
        cure();

    }

    @Override
    public void Fighting(int x, int y) {
        super.Fighting(x, y);
    }

    @Override
    public void cure() {
        System.out.println("Cure Grandpa");
        this.addHP_Remain(2);
        synchronized (maps){
            for(int i=-1;i<1;i++){
                for(int j=-1;j<1;j++){
                    if(i==0 && j==0){
                        continue;
                    }
                    int myX = location.getX()+i;
                    int myY = location.getY()+j;
                    System.out.println("myX="+myX);
                    System.out.println("myY="+myY);
                    if(insideMaps(myX,myY)){
                        Creature tmp = maps.getContent(myX,myY);
                        if(tmp!=null){
                            if(tmp.getCamp()==this.camp){
                                if(tmp.getHP_Remain()<(tmp.getHP_All())/2){
                                    System.out.println("给"+tmp.toString()+"+"+HP_ADD_JUSTICE*2+"HP");
                                    tmp.addHP_Remain(HP_ADD_JUSTICE*2);
                                }
                                else{
                                    System.out.println("给"+tmp.toString()+"+"+HP_ADD_JUSTICE+"HP");
                                    tmp.addHP_Remain(HP_ADD_JUSTICE);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void addOptions(Direction direction){
        options.add(direction);
    }
}
