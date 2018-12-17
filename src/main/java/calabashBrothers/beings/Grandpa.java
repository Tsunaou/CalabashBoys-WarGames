package calabashBrothers.beings;

import calabashBrothers.GUI.Maps;

/**
 * @ Author     ：Young
 * @ Description：葫芦娃爷爷
 */
public class Grandpa extends Creature implements CheeringUp{

    public Grandpa() {
        super("葫芦娃爷爷");
        this.filePath = this.getClass().getClassLoader().getResource("pic/grandpa.jpg");
    }

    @Override
    public void selfIntroduction() {
        super.selfIntroduction();
        System.out.println("我是"+this.name);
    }

    @Override
    public void CheeringUp(Maps<Creature> maps, int x, int y) {
        if(maps.empty(x,y)){
            maps.setContent(x,y,this);
        }
    }
}
