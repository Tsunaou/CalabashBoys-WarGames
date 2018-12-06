package calabashBrothers.beings;

import calabashBrothers.GUI.Maps;
import calabashBrothers.beings.enums.*;

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
        //System.out.println(this.getClass().getClassLoader().getResource("pic/"+indexPic+".jpg"));
        this.filePath = this.getClass().getClassLoader().getResource("pic/"+indexPic+".jpg");
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

    public int getRank() {
        return rank;
    }
}

