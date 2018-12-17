package calabashBrothers.beings;

import calabashBrothers.GUI.Coordinate;
import calabashBrothers.GUI.unit;

import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * @ Author     ：Young
 * @ Description：上帝创生，则有存在
 */

public class Beings {

    protected String name;
    protected URL filePath;  //新增，GUI使用直接寻址到resource的路径

    Coordinate location;

    public Beings(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public URL getFilePath() {
        return filePath;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public void setLocation(Coordinate location) {
        this.location = location;
    }

    public Coordinate getLocation() {
        return location;
    }

    public void beingSleep(int ms){
        try{
            TimeUnit.MILLISECONDS.sleep(ms);
        }catch (InterruptedException e){
            System.out.println(e.toString());
        }
    }
}

