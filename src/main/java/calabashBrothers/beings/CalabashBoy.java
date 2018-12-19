package calabashBrothers.beings;

import calabashBrothers.beings.enums.*;
import javafx.scene.image.Image;

/**
 * @ Author     ：Young
 * @ Description：有一种生物，名为葫芦娃
 */

public class CalabashBoy extends Creature implements Fighting{
    protected Color color;
    protected int rank;

    public CalabashBoy(int i) {
        super(EnumCalabashBoy.values()[i].getName());
        this.color = EnumCalabashBoy.values()[i].getColor();
        this.rank = EnumCalabashBoy.values()[i].getRank();
        int indexPic = rank+1;
        this.filePath = this.getClass().getClassLoader().getResource("pic/"+indexPic+".jpg");
        image = new Image(filePath.toString());
        imageAtk = new Image(this.getClass().getClassLoader().getResource("pic/fire.png").toString());
        setCamp(Camp.JUSTICE);  //阵营

        //设置属性
        setHP_All(HP_Calabash);
        setHP_Remain(HP_Calabash);
        setATK(ATK_Calabash);
        setDEF(DEF_Calabash);
        setAtkScale(Scale_Calabash);
    }

    @Override
    public void selfIntroduction() {
        super.selfIntroduction();
        System.out.println("我是"+this.name);
    }

    public int getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return super.toString()+this.rank;
    }
}

