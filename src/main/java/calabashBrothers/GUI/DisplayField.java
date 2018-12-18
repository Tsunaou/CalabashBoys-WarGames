package calabashBrothers.GUI;

import calabashBrothers.beings.Creature;
import calabashBrothers.beings.enums.Camp;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.ArrayList;
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
        player.setCycleCount(MediaPlayer.INDEFINITE); //设置循环播放（设置播放次数）
        player.setVolume(0.5);
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

    void changeMusic(String url,boolean replay){
        player.stop();
        String s2 = this.getClass().getClassLoader().getResource(url).toString();
        Media media2 = new Media(s2);
        player = new MediaPlayer(media2);
        if(replay){
            player.setCycleCount(MediaPlayer.INDEFINITE); //设置循环播放（设置播放次数）
        }
        player.setVolume(0.5);
        player.play();
    }

    void display(){
        boolean dangerFlag = false;
        while (Running){
            synchronized (maps){
//                maps.refreshMaps();
                maps.showMaps();
                if(firstDisplay){
                    displaySleep(3);
                    firstDisplay = false;
                }
            }
            synchronized (maps){
                int justiceCnts = maps.getJusticeCounts();
                int evilCnts = maps.getEvilCounts();
                if((justiceCnts+evilCnts)<=10 && !dangerFlag){
                    changeMusic("media/luffy.mp3",true);
                    System.err.println("人数小于10人");
                    dangerFlag = true;
                }
                if(justiceCnts==0 && evilCnts!=0){
                    maps.gameOver(Camp.EVIL);
                    changeMusic("media/lose.mp3",false);
                    this.Running = false;
                }
                if(evilCnts==0 && justiceCnts!=0){
                    maps.gameOver(Camp.JUSTICE);
                    changeMusic("media/win.mp3",false);
                    this.Running = false;
                }

            }
            displaySleep(1000);
        }
    }

    public void run(){
        display();
//        player.stop();
    }

}
