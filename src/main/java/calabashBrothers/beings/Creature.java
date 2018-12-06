package calabashBrothers.beings;


/**
 * @ Author     ：Young
 * @ Description：世界初生，有了生物
 */


public class Creature extends Beings {

    public Creature(String name) {
        super(name);
    }

    public void selfIntroduction() {
        System.out.println("我的种类是" + this.getClass().getSimpleName());
    }
}

