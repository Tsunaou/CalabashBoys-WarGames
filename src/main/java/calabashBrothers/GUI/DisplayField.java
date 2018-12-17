package calabashBrothers.GUI;

import calabashBrothers.beings.Creature;

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
                maps.showMaps();
                if(firstDisplay){
                    displaySleep(3);
                    firstDisplay = false;
                }
            }
            displaySleep(3000);
        }
    }

    public void run(){
        display();
    }

}
