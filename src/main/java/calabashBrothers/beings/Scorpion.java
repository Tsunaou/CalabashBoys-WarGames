package calabashBrothers.beings;

import calabashBrothers.GUI.Maps;

/**
 * @ Author     ：Young
 * @ Description：蝎子精
 */
public class Scorpion extends Monster implements Fighting{
    public Scorpion() {
        this.name="蝎子精";
        this.filePath = this.getClass().getClassLoader().getResource("pic/scorpion.jpg");
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
