package calabashBrothers.GUI;

import calabashBrothers.beings.Creature;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.concurrent.TimeUnit;

/**
 * @ Author     ：Young
 * @ Date       ：Created in 18:58 2018/12/17
 * @ Description：实时刷新屏幕
 */
public class DisplayField implements Runnable{

    private static Maps<Creature> maps;   //static变量，所有的生物共享一个maps
    private boolean Running = true;
    private boolean firstDisplay = true;
    private String s;
    private Media media;
    private MediaPlayer player;


    public DisplayField(){
        s = this.getClass().getClassLoader().getResource("media/Shediao.mp3").toString();
        media = new Media(s);
        player = new MediaPlayer(media);
        player.play();
    }

    public static Maps<Creature> getMaps() {
        return maps;
    }

    public static void setMaps(Maps<Creature> maps) {
        DisplayField.maps = maps;
    }

    public boolean isRunning() {
        return Running;
    }

    public void setRunning(boolean running) {
        Running = running;
    }


    private void displaySleep(int ms){
        try{
            TimeUnit.MILLISECONDS.sleep(ms);
        }catch (InterruptedException e){
            System.out.println(e.toString());
        }
    }

    void display(){
        while (Running){
            synchronized (maps){
                maps.refreshMaps();
                maps.showMaps();
                if(firstDisplay){
                    displaySleep(3);
                    firstDisplay = false;
                }
            }
            synchronized (maps){
                if(maps.getCounts()<=10){
//                    player.stop();
//                    String s2 = this.getClass().getClassLoader().getResource("media/luffy.mp3").toString();
//                    Media media2 = new Media(s2);
//                    player = new MediaPlayer(media2);
//                    player.play();
                    System.err.println("人数小于10人");
                }
            }

            displaySleep(1500);
        }
    }

    public void run(){
        display();
    }

}
