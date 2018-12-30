package calabashBrothers.GUI;


import javafx.scene.control.Label;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @ Author     ：Young
 * @ Date       ：Created in 11:52 2018/12/22
 * @ Description：时间控制器，用于控制休眠
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
