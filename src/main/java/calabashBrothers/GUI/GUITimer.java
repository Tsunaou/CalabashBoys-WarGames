package calabashBrothers.GUI;

import java.util.concurrent.TimeUnit;

/**
 * @ Author     ：Young
 * @ Date       ：Created in 11:52 2018/12/22
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */
public class GUITimer {
    public static void displaySleep(int ms){
        try{
            TimeUnit.MILLISECONDS.sleep(ms);
        }catch (InterruptedException e){
            System.out.println(e.toString());
        }
    }
}
