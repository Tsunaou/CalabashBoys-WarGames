package calabashBrothers.formation;
import calabashBrothers.formation.*;
/**
 * @ Author     ：Young
 * @ Date       ：Created in 14:49 2018/12/17
 * @ Description：产生阵型的工厂类
 */
public class FormationFactory {
    public static ChangShe createChangShe(int x, int y){
        return new ChangShe(x,y);
    }
    public static ChongE createChongE(int x, int y){
        return new ChongE(x,y);
    }
    public static FangYuan createFangYuan(int x, int y){
        return new FangYuan(x,y);
    }
    public static FengShi createFengShi(int x, int y){
        return new FengShi(x,y);
    }
    public static HeYi createHeYi(int x, int y){
        return new HeYi(x,y);
    }
    public static YanXing createYanXing(int x, int y){
        return new YanXing(x,y);
    }
    public static YanYue createYanYue(int x, int y){
        return new YanYue(x,y);
    }
    public static YuLin createYuLin(int x, int y){
        return new YuLin(x,y);
    }
}
