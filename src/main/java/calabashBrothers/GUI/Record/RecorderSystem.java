package calabashBrothers.GUI.Record;

import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;

/**
 * @ Author     ：Young
 * @ Date       ：Created in 10:21 2018/12/21
 * @ Description：负责记录的保存和读取
 */
public class RecorderSystem {

    Window window;

    public RecorderSystem(Window window) {
        this.window = window;
    }

    public void openRecord(){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.setTitle("读档");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(window);
        System.out.println(file);
    }

    public void saveRecord(){
        FileChooser fileChooser1 = new FileChooser();
        fileChooser1.setTitle("存档");
        File file = fileChooser1.showSaveDialog(window);
        System.out.println(file);
    }

}
