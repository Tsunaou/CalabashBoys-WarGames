package calabashBrothers.beings;

import calabashBrothers.GUI.Maps;

/**
 * @ Author     ：Young
 * @ Description：妖怪
 */
public class Monster extends Creature implements Fighting{
    public Monster() {
        super("小喽啰");
        this.filePath = this.getClass().getClassLoader().getResource("pic/lolo.jpg");
    }

    @Override
    public void selfIntroduction() {
        super.selfIntroduction();
        System.out.println("我是"+this.name);
    }

    @Override
    public void Fighting(Maps maps, int x, int y) {
        //TODO
    }
}
