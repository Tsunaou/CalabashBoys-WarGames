package calabashBrothers.beings;

import calabashBrothers.GUI.Maps;
import calabashBrothers.beings.enums.Camp;
import javafx.scene.image.Image;

/**
 * @ Author     ：Young
 * @ Description：葫芦娃爷爷
 */
public class Grandpa extends Creature implements CheeringUp{

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
}
