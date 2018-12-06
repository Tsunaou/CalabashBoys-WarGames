package calabashBrothers;

import calabashBrothers.beings.Creature;
import calabashBrothers.beings.enums.CalabashName;
import calabashBrothers.beings.enums.EnumCalabashBoy;

import java.awt.Color;
import java.awt.Point;
import java.util.Hashtable;

import javax.swing.*;


/**
 * @ Author     ：Young
 * @ Description：用于展示战场
 */
public class BattleField {

    private JFrame f;
    private int row;
    private int col;
    private int size;
    private int width;
    private int height;
    private Hashtable<String,Integer> nameToRank = new Hashtable<String, Integer>();


    public BattleField(int row, int col, int size) {
        f = new JFrame("葫芦娃 VS 妖怪");
        this.row = row;
        this.col = col;
        this.size = size;
        this.width = (col+1) * size-30;
        this.height = (row+1) * size;
        for (int i = 0; i < 7; i++) {
            nameToRank.put(CalabashName.values()[i].toString(),i);
        }

        f.setSize(width, height);
        //窗口设置位置
        Point point = new Point(350, 200);
        f.setLocation(point);
    }

    JLabel FitInBlank(){
        JLabel tmp = new JLabel();
        tmp.setSize(size, size);
        tmp.setLocation(row*size,col* size);    //设置标签位置
        tmp.setBorder(BorderFactory.createLineBorder(Color.black));  //设置边界为黑色
        return  tmp;
    }

    void ClearBattleField(){
        f.getContentPane().removeAll();
    }

    void ShowBattleField(Maps<Creature> maps) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                ImageIcon  icon = new ImageIcon("");

                Creature tmp = maps.getContent(i,j);
                if(tmp != null){
                    icon = new ImageIcon(tmp.getFilePath());
                }else{
//                    System.out.println(this.getClass().getClassLoader().getResource("pic/back.jpg").getPath());
                    icon = new ImageIcon(this.getClass().getClassLoader().getResource("pic/back.jpg"));
                }
                icon.setImage(icon.getImage().getScaledInstance(size, size,0));
                JLabel l = new JLabel(icon,JLabel.CENTER);
                l.setSize(size, size);
                l.setLocation(j * size, i * size);    //设置标签位置
                l.setBorder(BorderFactory.createLineBorder(Color.black));  //设置边界为黑色
                f.add(l);            //添加标签
            }
        }

        f.add(FitInBlank());            //添加标签
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
