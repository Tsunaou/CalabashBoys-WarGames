package calabashBrothers.beings;


import calabashBrothers.GUI.Maps;

/**
 * @ Author     ：Young
 * @ Description：世界初生，有了生物
 */


public class Creature extends Beings {

    private static Maps maps;   //static变量，所有的生物共享一个maps

    public Creature(String name) {
        super(name);
    }

    public void selfIntroduction() {
        System.out.println("我的种类是" + this.getClass().getSimpleName());
    }

    public static Maps getMaps() {
        return maps;
    }

    public static void setMaps(Maps maps) {
        Creature.maps = maps;
    }
}

