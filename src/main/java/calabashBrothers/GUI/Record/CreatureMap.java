package calabashBrothers.GUI.Record;

import calabashBrothers.beings.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @ Author     ：Young
 * @ Date       ：Created in 21:06 2018/12/19
 * @ Description：战场上存在的生物映射（保存文件用）
 */

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
        creatures.add(new DeathObject());

        //开始添加映射关系
        for(int i=0;i<creatures.size();i++){
//            System.out.println("Add mapping "+creatures.get(i).toString());
            creatureMaps.put(creatures.get(i).toString(),creatures.get(i));
        }
    }

    public Creature getClassByString(String key){
//        System.out.println("getClassByString "+key);
        return creatureMaps.get(key);
    }

}