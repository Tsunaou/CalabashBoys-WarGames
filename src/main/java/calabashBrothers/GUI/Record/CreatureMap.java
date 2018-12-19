package calabashBrothers.GUI.Record;

import calabashBrothers.beings.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @ Author     ：Young
 * @ Date       ：Created in 21:06 2018/12/19
 * @ Description：战场上存在的生物映射（保存文件用）
 */

//public enum CreatureIndex{
//    ONE,TWO,THREE,FOUR,FIVE,SIX,SEVEN,//葫芦娃
//    GRANDPA,MONSTER,SNAKE,SCORPION
//}

public class CreatureMap{

    static HashMap<String, Creature> creatureMaps;//通过该表，可以得到生物种类和类的映射关系
    ArrayList<Creature> creatures = new ArrayList<Creature>();

    public CreatureMap() {
        creatureMaps = new HashMap<>();
        //初始化各个葫芦娃
        for(int i = 0; i < 7; ++i) {
            creatures.add(new CalabashBoy(i));
        }
        creatures.add(new Grandpa());
        creatures.add(new Monster());
        creatures.add(new Snake());
        creatures.add(new Scorpion());

        //开始添加映射关系
        for(int i=0;i<creatures.size();i++){
            creatureMaps.put(creatures.get(i).toString(),creatures.get(i));
        }
    }

    static Creature getClassByString(String key){
        return creatureMaps.get(key);
    }

}