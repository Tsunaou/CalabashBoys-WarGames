package calabashBrothers.GUI.Record;

import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

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

    //读档
    public ArrayList<Recorder>  openRecord(){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("HULUWA files (*.huluwa)", "*.huluwa");
        fileChooser.setTitle("读档");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(window);
        System.out.println(file);
        //没有选择要读取的文件

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
            e.printStackTrace();
        }


        return  recorders;
    }

    //存档
    public void saveRecord(ArrayList<Recorder> recorders){
        File file = new File("record.huluwa");

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
            e.printStackTrace();
        }



    }

}
