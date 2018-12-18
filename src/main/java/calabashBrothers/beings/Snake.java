package calabashBrothers.beings;

import calabashBrothers.GUI.Maps;
import javafx.scene.image.Image;

/**
 * @ Author     ：Young
 * @ Description：蛇精
 */
public class Snake extends Monster implements CheeringUp{
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
}
