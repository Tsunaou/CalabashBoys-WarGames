package calabashBrothers.GUI;

import calabashBrothers.GUI.Record.Recorder;
import calabashBrothers.beings.Creature;
import calabashBrothers.beings.enums.Camp;
import javafx.stage.Window;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @ Author     ：Young
 * @ Date       ：Created in 18:58 2018/12/17
 * @ Description：实时刷新屏幕
 */
public class DisplayField implements Runnable, Callable<String> {

    private static Maps<Creature> maps;   //static变量，所有的生物共享一个maps
    private static int DISPLAY_HZ = 500;       //刷新频率
    private boolean Running = true;
    private boolean Replaying = false;
    private boolean firstDisplay = true;
    private boolean fightingEnd = false;
    private String s;
    private Media media;
    private MediaPlayer player;
    ArrayList<Recorder> recorder = new ArrayList<>();   //回放记录存储

    private Window window;  //窗口
    private boolean windowFlag = false;
    private boolean disPlaying = true;

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

    public static void setDisplayHz(int displayHz) {
        DISPLAY_HZ = displayHz;
    }

    public void setRunning(boolean running) {
        Running = running;
        System.out.println("1.Display running false");
    }

    public void setReplaying(boolean replaying) {
        Replaying = replaying;
        System.out.println("2.Display setReplaying ture");
    }

    public boolean isRunning() {
        return Running;
    }

    public boolean isReplaying() {
        return Replaying;
    }

    public void setWindow(Window window) {
        this.window = window;
        windowFlag = true;
    }

    public void setRecorder(ArrayList<Recorder> recorder) {
        this.recorder = recorder;
    }

    public void setDisPlaying(boolean disPlaying) {
        this.disPlaying = disPlaying;
    }

    //切换音乐
    private void changeMusic(String url, boolean replay){
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

    //回放记录文件
    public void replayRecordList( ArrayList<Recorder> r){
        changeMusic("media/Shediao.mp3",true);
        System.out.println("Replaying,size="+r.size());
        int END_SIZE = r.size();
        for (int i=0;i<END_SIZE;i++){
            synchronized (maps){
                System.out.println("replayMaps,i="+i);
                maps.replayMaps(r.get(i));
            }
            GUITimer.displaySleep(DISPLAY_HZ);
            if(i>=r.size()){
                break;
            }
        }
        synchronized (maps){
            int justiceCnts = maps.getJusticeCounts();
            int evilCnts = maps.getEvilCounts();
            //妖怪获胜
            if(justiceCnts==0 && evilCnts!=0){
                maps.gameOver(Camp.EVIL,window);
                changeMusic("media/lose.mp3",false);
            }
            //葫芦娃获胜
            if(evilCnts==0 && justiceCnts!=0){
                maps.gameOver(Camp.JUSTICE,window);
                changeMusic("media/win.mp3",false);
            }
        }

        Replaying = false;
        System.out.println("Replaying Ending");
    }

    //播放
    private void display(){
        boolean dangerFlag = false;
        while (Running){
            System.out.println("display Running");
            synchronized (maps){
                maps.showMaps();
                if(firstDisplay){
                    GUITimer.displaySleep(3);
                    firstDisplay = false;
                }
            }
            synchronized (maps){
                int justiceCnts = maps.getJusticeCounts();
                int evilCnts = maps.getEvilCounts();
                //人数少于10人的时候，修改背景音乐
                if((justiceCnts+evilCnts)<=10 && !dangerFlag){
                    changeMusic("media/luffy.mp3",true);
                    System.err.println("人数小于10人");
                    dangerFlag = true;
                }
                //妖怪获胜
                if(justiceCnts==0 && evilCnts!=0){
                    maps.gameOver(Camp.EVIL,window);
                    changeMusic("media/lose.mp3",false);
                    this.Running = false;
                    fightingEnd = true;
                }
                //葫芦娃获胜
                if(evilCnts==0 && justiceCnts!=0){
                    maps.gameOver(Camp.JUSTICE,window);
                    changeMusic("media/win.mp3",false);
                    this.Running = false;
                    fightingEnd = true;
                }
                if(fightingEnd){
                    GUITimer.displaySleep(5000);
                    player.stop();
                }

            }
            GUITimer.displaySleep(DISPLAY_HZ);
        }
    }

    //回放
    void replay(){
        while (!Replaying){
            GUITimer.displaySleep(500);
            System.out.println("waiting for replay");
        }
        if(Replaying){
            this.replayRecordList(recorder);
            display();
            Replaying = false;
        }
    }

    //run方法
    public void run(){
        display();
    }

    //call方法
    public String call(){
        //等待外界的回放信号
        replay();
        return "replay";
    }


}
