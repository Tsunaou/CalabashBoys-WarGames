package calabashBrothers.beings;

import calabashBrothers.GUI.Maps;
import javafx.scene.image.Image;

/**
 * @ Author     ：Young
 * @ Description：蝎子精
 */
public class Scorpion extends Monster implements Fighting{
    public Scorpion() {
        this.name="蝎子精";
        this.filePath = this.getClass().getClassLoader().getResource("pic/scorpion.jpg");
        image = new Image(filePath.toString());
        imageAtk = new Image(this.getClass().getClassLoader().getResource("pic/killer.png").toString());
    }

    @Override
    public void selfIntroduction() {
        super.selfIntroduction();
        System.out.println("我是"+this.name);
    }

    @Override
    public void Fighting(int x, int y) {
        //TODO
    }
}
