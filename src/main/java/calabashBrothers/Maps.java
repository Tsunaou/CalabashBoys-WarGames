package calabashBrothers;

import calabashBrothers.beings.Creature;

import javax.swing.*;
import java.util.ArrayList;

/**
 * @ Author     ：Young
 * @ Description：战场的地图，二维平面，每个点是一个Unit
 */
public class Maps<T> {
    private int rows;
    private int cols;
    private ArrayList<ArrayList<unit<T>>> maps; //用容器表示二维数组，再加上泛型的unit，有点复杂感觉

    public Maps(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        maps = new ArrayList<ArrayList<unit<T>>>();
        for (int i = 0; i <rows ; i++) {
            maps.add(new ArrayList<unit<T>>()); //增加一行
            for (int j = 0; j <cols ; j++) { //增加一列
                maps.get(i).add(new unit<T>(i,j));
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public  T getContent(int x,int y){
        return maps.get(x).get(y).getContent();
    }

    public boolean empty(int x,int y){
        return  maps.get(x).get(y).none();
    }

    public  void setContent(int x,int y,T content){
        maps.get(x).get(y).setContent(content);
    }


    public ArrayList<ArrayList<unit<T>>> getMaps() {
        return maps;
    }

    public void showMaps(){
        for (int i = 0; i <rows ; i++) {
            for (int j = 0; j <cols ; j++) {
                T tmp = maps.get(i).get(j).getContent();
                if( tmp != null){
                    switch (tmp.getClass().getSimpleName()){
                        case "CalabashBoy":{
                            System.out.print("C");
                        }break;

                        case "Grandpa":{
                            System.out.print("G");
                        }break;

                        case "Monster":{
                            System.out.print("M");
                        }break;

                        case "Scorpion": {
                            System.out.print("L");
                        }break;

                        case"Snake":{
                                System.out.print("S");
                        }break;
                        default:{
                            System.out.println("Error when showMaps");
                        }

                    }
                }else{
                    System.out.print("-");
                }
            }
            System.out.println(" ");
        }
        System.out.println(" ");
    }

    public void removeMaps(){
        for (int i = 0; i <rows ; i++) {
            for (int j = 0; j <cols ; j++) {
                maps.get(i).get(j).removeContent();
            }
        }
    }
}
