package calabashBrothers.GUI;

/**
 * @ Author     ：Young
 * @ Date       ：Created in 14:05 2018/12/17
 * @ Description：各种参数
 */
public interface Config {
    final int Height = 15;  //棋盘布局的高度
    final int Width = 15;   //棋盘布局的宽度

    //基准伤害
    final int DAMAGE_PER = 15;

    //攻击力
    final int ATK_Creature = 8;
    final int ATK_Calabash = 10;
    final int ATK_Grandpa = 6;
    final int ATK_Monster = 8;
    final int ATK_Scorpion = 12;
    final int ATK_Snake = 9;

    //防御力
    final int DEF_Creature = 5;
    final int DEF_Calabash = 10;
    final int DEF_Grandpa = 20;
    final int DEF_Monster = 8;
    final int DEF_Scorpion = 12;
    final int DEF_Snake = 10;

    //生命值
    final int HP_Creature = 100;
    final int HP_Calabash = 250;
    final int HP_Grandpa = 100;
    final int HP_Monster = 150;
    final int HP_Scorpion = 300;
    final int HP_Snake = 100;

    //攻击范围
    final int Scale_Creature = 1;
    final int Scale_Calabash = 1;
    final int Scale_Grandpa = 1;
    final int Scale_Monster = 1;
    final int Scale_Scorpion = 2;
    final int Scale_Snake = 1;

    //回复效果
    final int HP_ADD_JUSTICE = 20;
    final int HP_ADD_MONSTER = 10;

}
