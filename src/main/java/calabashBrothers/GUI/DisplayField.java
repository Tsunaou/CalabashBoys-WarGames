package calabashBrothers.GUI;

import calabashBrothers.GUI.Record.Recorder;
import calabashBrothers.beings.Creature;
import calabashBrothers.beings.enums.Camp;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 * @ Author     ：Young
 * @ Date       ：Created in 18:58 2018/12/17
 * @ Description：实时刷新屏幕
 */
public class DisplayField implements Runnable{

    private static Maps<Creature> maps;   //static变量，所有的生物共享一个maps
    private int DISPLAY_HZ = 250;       //刷新频率
    private boolean Running = true;
    private boolean Replaying = false;
    private boolean firstDisplay = true;
    private boolean fightingEnd = false;
    private String s;
    private Media media;
    private MediaPlayer player;
    ArrayList<Recorder> recorder = new ArrayList<>();   //回放记录存储


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
        System.out.println("1.Display running false");
    }

    public void setReplaying(boolean replaying) {
        Replaying = replaying;
        System.out.println("2.Display setReplaying ture");
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
                    fightingEnd = true;
                    Replaying = true;
                }
                if(evilCnts==0 && justiceCnts!=0){
                    maps.gameOver(Camp.JUSTICE);
                    changeMusic("media/win.mp3",false);
                    this.Running = false;
                    fightingEnd = true;
                    Replaying = true;
                }
                if(fightingEnd){
                    displaySleep(3000);
                }

            }
            displaySleep(DISPLAY_HZ);
        }
    }

    void replay(){

        boolean endFlag = false;
        if(!endFlag){
            synchronized (maps){
                recorder = maps.getRecordList();
                System.out.println("getRecordList");
                if(!fightingEnd){
                    maps.gameOver(Camp.JUSTICE);
                }
                player.stop();
                endFlag = true;
            }
        }


//        Iterator<Recorder> it = recorder.iterator(); //由于迭代器的实现机制，会触发同时写异常

        if(Replaying){
            changeMusic("media/Shediao.mp3",true);
            System.out.println("Replaying,size="+recorder.size());
            int END_SIZE = recorder.size();
            for (int i=0;i<END_SIZE;i++){
                synchronized (maps){
                    System.out.println("replayMaps,i="+i);
                    maps.replayMaps(recorder.get(i));
                }
                displaySleep(DISPLAY_HZ);
                if(i>=recorder.size()){
                    break;
                }
            }
            Replaying = false;
            System.out.println("Replaying Ending");
        }
    }

    public void run(){
        display();
        //状态切换后才会回放
        System.out.println("Display Run");
        replay();
    }

}
