package calabashBrothers.GUI.Record;

import calabashBrothers.beings.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @ Author     ：Young
 * @ Date       ：Created in 21:31 2018/12/23
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */
public class CreatureMapTest {

    @Before
    public void setUp() throws Exception {
        System.out.println("before");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("after");
    }

    @Test
    public void getClassByString() {
        CreatureMap cMap = new CreatureMap();
        for(int i=0;i<7;i++){
            System.out.println(cMap.getClassByString("CalabashBoy"+i));
        }
    }
}