package calabashBrothers.beings;

import calabashBrothers.GUI.Maps;
import calabashBrothers.beings.enums.Camp;
import javafx.scene.image.Image;

/**
 * @ Author     ：Young
 * @ Description：妖怪
 */
public class Monster extends Creature{
    public Monster() {
        super("小喽啰");
        this.filePath = this.getClass().getClassLoader().getResource("pic/lolo.jpg");
        image = new Image(filePath.toString());
        imageAtk = new Image(this.getClass().getClassLoader().getResource("pic/storm.png").toString());
        setCamp(Camp.EVIL);  //阵营
        //设置属性
        setHP_All(HP_Monster);
        setHP_Remain(HP_Monster);
        setATK(ATK_Monster);
        setDEF(DEF_Monster);
        setAtkScale(Scale_Monster);
    }

    @Override
    public void selfIntroduction() {
        super.selfIntroduction();
        System.out.println("我是"+this.name);
    }

}
