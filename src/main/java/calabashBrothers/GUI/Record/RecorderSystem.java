package calabashBrothers.GUI.Record;

import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.*;
import java.util.ArrayList;

/**
 * @ Author     ：Young
 * @ Date       ：Created in 10:21 2018/12/21
 * @ Description：负责记录的保存和读取
 */
public class RecorderSystem {

    private Window window;

    public RecorderSystem(Window window) {
        this.window = window;
    }

    public ArrayList<Recorder>  openRecord(){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("HULUWA files (*.huluwa)", "*.huluwa");
        fileChooser.setTitle("读档");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(window);
        System.out.println(file);

        ArrayList<Recorder> recorders = new ArrayList<>();

        try{
            ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream(file));
            //读入葫芦娃标志
            String s = (String)in.readObject();
            //读入记录序列
            recorders = (ArrayList<Recorder>)in.readObject();
            System.out.println(recorders);
        }catch (ClassNotFoundException | IOException e){
            System.out.println(e);
        }


        return  recorders;
    }

    public void saveRecord(ArrayList<Recorder> recorders){
//        FileChooser fileChooser1 = new FileChooser();
//        fileChooser1.setTitle("存档");
//        System.out.println(recorders);
//        File file = fileChooser1.showSaveDialog(window);
//        System.out.println(file);
        File file = new File("H:\\JavaFinalPrepare\\calabashBoys\\src\\main\\resources\\record\\record.huluwa");

        try{
            //将记录序列化输出
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream(file));
            //写入葫芦娃标志
            out.writeObject("Calabash records\n");
            //写入记录序列
            out.writeObject(recorders);
            out.close(); // Also flushes output
        }catch (IOException e){
            System.out.println(e);
        }



    }

}
