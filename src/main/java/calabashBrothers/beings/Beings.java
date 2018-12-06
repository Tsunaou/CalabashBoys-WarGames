package calabashBrothers.beings;

import java.net.URL;

/**
 * @ Author     ：Young
 * @ Description：上帝创生，则有存在
 */

public class Beings {

    protected String name;
    protected URL filePath;  //新增，GUI使用直接寻址到resource的路径

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
}
