package calabashBrothers.beings;

import calabashBrothers.GUI.Coordinate;
import org.junit.Test;

import java.util.Date;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * @ Author     ：Young
 * @ Date       ：Created in 14:16 2018/12/30
 * @ Description：测试，主要针对随机移动时候的越界
 */
public class OutOfBounceTest {

    @Test @Deprecated
    public void outOfBounceTest() {
        Random rx = new Random(new Date().getSeconds());
        Random ry = new Random(new Date().getSeconds());
        Creature cTest = new Creature("test");
        int insidesCnts = 0;
        int outsideCnts = 0;

        for(int i=0;i<100;i++){
            int x = -10+rx.nextInt(25);
            int y = -10+ry.nextInt(25);
            try{
                if(cTest.insideMaps(x,y)){
                    insidesCnts++;
                }else{
                    throw new OutBounceException("outSides",new Coordinate(x,y));
                }
            }catch (OutBounceException e){
                outsideCnts++;
                System.out.println("坐标"+e.getOutPos().toString()+"越界,抛弃该位置");
            }

        }
    }
}