package calabashBrothers.beings;

import calabashBrothers.beings.enums.Camp;
import javafx.scene.image.Image;

/**
 * @ Author     ：Young
 * @ Date       ：Created in 9:50 2018/12/18
 * @ Description：死亡的物体
 */
public class DeathObject extends Creature{
    public DeathObject() {
        super("死亡物体");
        this.filePath = this.getClass().getClassLoader().getResource("pic/ghost.png");
        image = new Image(filePath.toString());

        System.out.println(filePath);
        setCamp(Camp.DEAD);  //阵营
    }

    @Override
    public void selfIntroduction() {
        super.selfIntroduction();
        System.out.println("我是"+this.name);
    }
}
