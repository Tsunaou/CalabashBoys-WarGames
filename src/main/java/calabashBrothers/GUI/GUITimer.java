package calabashBrothers.GUI;


import javafx.scene.control.Label;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @ Author     ：Young
 * @ Date       ：Created in 11:52 2018/12/22
 * @ Description：${description}
 * @ Modified By：
 * @Version: $version$
 */
public class GUITimer {
    private static Label timeLabel;
    private static int initTimes = 0;
    public static void displaySleep(int ms){
        try{
            TimeUnit.MILLISECONDS.sleep(ms);
        }catch (InterruptedException e){
            System.out.println(e.toString());
        }
    }

    @Deprecated
    public static void setTimeLabel(Label timeLabel) {
        GUITimer.timeLabel = timeLabel;
        timeLabel.setText(String.valueOf(initTimes));
    }
}
