package calabashBrothers.beings;

import calabashBrothers.GUI.Maps;
import calabashBrothers.beings.enums.Camp;

/**
 * @ Author     ：Young
 * @ Description：妖怪
 */
public class Monster extends Creature implements Fighting{
    public Monster() {
        super("小喽啰");
        this.filePath = this.getClass().getClassLoader().getResource("pic/lolo.jpg");
        setCamp(Camp.EVIL);  //阵营
    }

    @Override
    public void selfIntroduction() {
        super.selfIntroduction();
        System.out.println("我是"+this.name);
    }

}
