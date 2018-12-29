package calabashBrothers.beings;

import calabashBrothers.GUI.Maps;
import javafx.scene.image.Image;

/**
 * @ Author     ：Young
 * @ Description：蛇精
 */
public class Snake extends Monster implements CheeringUp,Curable{
    public Snake() {
        this.name="蛇精";
        this.filePath = this.getClass().getClassLoader().getResource("pic/snake.jpg");
        image = new Image(filePath.toString());
        imageAtk = new Image(this.getClass().getClassLoader().getResource("pic/poison.png").toString());

        //设置属性
        setHP_All(HP_Snake);
        setHP_Remain(HP_Snake);
        setATK(ATK_Snake);
        setDEF(DEF_Snake);
        setAtkScale(Scale_Snake);
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
    public void cure() {
        System.out.println("Cure Snake");
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
                                System.out.println("给"+tmp.toString()+"+"+HP_ADD_MONSTER+"HP");
                                System.out.println(tmp.toString()+"HP_Remain"+tmp.getHP_Remain());
                                tmp.addHP_Remain(HP_ADD_MONSTER);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public synchronized void moveRandom() {
        super.moveRandom();
        cure();
    }
}
